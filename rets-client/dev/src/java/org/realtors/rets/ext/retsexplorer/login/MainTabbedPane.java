package org.realtors.rets.ext.retsexplorer.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTabbedPane;

import org.realtors.rets.ext.retsexplorer.ProgressBarDialog;
import org.realtors.rets.ext.retsexplorer.login.SourceSplitView.Custom;
import org.realtors.rets.ext.retsexplorer.util.ButtonTabComponent;
import org.realtors.rets.ext.retsexplorer.util.ErrorPopupActionListener;
import org.realtors.rets.ext.retsexplorer.util.GuiKeyBindings;
import org.realtors.rets.ext.retsexplorer.util.QueryManager;
import org.realtors.rets.ext.util.RetsClientConfig;

public class MainTabbedPane extends JTabbedPane {
	private List<RetsClientConfig> retsConfigs;
	private ActionListener closeTabActionListener = createCloseTabActionListener();
	private QueryManager qm;
	private final Custom customSourceComponents;
	
	public MainTabbedPane(QueryManager qm, List<RetsClientConfig> retsConfigs, Custom customSourceComponents) {
		ProgressBarDialog.update(500,"Creating Initial Login Tab...");
		this.qm = qm;
		this.retsConfigs = retsConfigs;
		this.customSourceComponents = customSourceComponents;
		this.setDoubleBuffered(true);
		createTab();
		GuiKeyBindings.setLoginAction(this); //TODO: shift this to a lower level
	}
	
	public void createTab() { //don't login with this, just create the new window
		SourceSplitView splitPane = new SourceSplitView(this.qm, this.retsConfigs, loginSuccessActionListener(),this.customSourceComponents);
		this.addTab("login", splitPane);
	}
	
	public ActionListener loginSuccessActionListener() { //this should be called once you successfully login to a server
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) { //set name of tab to the server that you are now logged in to
				for (int i = 0; i < MainTabbedPane.this.getTabCount(); i++) {
					SourceSplitView sv = (SourceSplitView) MainTabbedPane.this.getComponentAt(i);
					if (sv.getSourceName() != null) {
						MainTabbedPane.this.setTabComponentAt(i, new ButtonTabComponent(MainTabbedPane.this, sv.getSourceName()));
						MainTabbedPane.this.setTitleAt(i, sv.getSourceName()); //so that I can reference the tabs later by source name
					}
				}
			}
		};
		return al;
	}
	
	public ActionListener getCloseTabActionListener() {
		return this.closeTabActionListener;
	}
	
	public void closeCurrentTab() { //TODO: could probably get rid of this method now, since I override remove() itself
		int index = this.getSelectedIndex();
		if (index == -1) return; //this should never happen...
		remove(index);
	}
	
	private ActionListener createCloseTabActionListener() {
		ActionListener al = new ErrorPopupActionListener(){
			@Override
			public void action() throws Exception {
				closeCurrentTab();
			}
		};
		return al;
	}
	
	@Override
	public void remove(int index) {
        removeTabAt(index);
        if (this.getTabCount() == 0) {
			this.createTab();
		}
    }
}