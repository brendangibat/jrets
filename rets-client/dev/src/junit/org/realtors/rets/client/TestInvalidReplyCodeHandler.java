package org.realtors.rets.client;

final class TestInvalidReplyCodeHandler implements InvalidReplyCodeHandler {
    private int replyCode;
    
    @Override
	public void invalidRetsReplyCode(int code) throws InvalidReplyCodeException {
        throw new InvalidReplyCodeException(code);
    }
    
    @Override
	public void invalidRetsStatusReplyCode(int code) throws InvalidReplyCodeException {
        this.replyCode = code;
    }

    public int getReplyCode() {
        return this.replyCode;
    }
}