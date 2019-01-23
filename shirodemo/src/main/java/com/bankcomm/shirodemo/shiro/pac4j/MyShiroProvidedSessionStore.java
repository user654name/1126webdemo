package com.bankcomm.shirodemo.shiro.pac4j;

import org.apache.shiro.session.Session;

public class MyShiroProvidedSessionStore extends MyShiroSessionStore{

	
	private Session session;
	
	
	public MyShiroProvidedSessionStore(Session session) {
		this.session = session;
	}
	

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
    protected Session getSession(final boolean createSession) {
        return getSession();
    }
	
	
}
