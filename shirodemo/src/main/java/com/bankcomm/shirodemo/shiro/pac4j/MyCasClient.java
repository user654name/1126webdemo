package com.bankcomm.shirodemo.shiro.pac4j;


import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.redirect.RedirectAction;

/**
 * @author Tianqi.Zhang
 * @date 2018/12/28
 * @time 16:44
 * @package com.bankcomm.shirodemo.shiro.pac4j
 * @project 1126webdemo
 * @description
 */
//@Component
public class MyCasClient extends org.pac4j.cas.client.CasClient {
    public MyCasClient() {
        super();
    }

    public MyCasClient(CasConfiguration configuration) {
        super(configuration);
    }

    /*
     * (non-Javadoc)
     * @see org.pac4j.core.client.IndirectClient#getRedirectAction(org.pac4j.core.context.WebContext)
     */

    @Override
    public RedirectAction getRedirectAction(WebContext context) {
        return super.getRedirectAction(context);
//        this.init();
//        if (getAjaxRequestResolver().isAjax(context)) {
//            this.logger.info("AJAX request detected -> returning the appropriate action");
//            RedirectAction action = getRedirectActionBuilder().redirect(context);
//            this.cleanRequestedUrl(context);
//            return getAjaxRequestResolver().buildAjaxResponse(action.getLocation(), context);
//        } else {
//            final String attemptedAuth = (String)context.getSessionStore().get(context, this.getName() + ATTEMPTED_AUTHENTICATION_SUFFIX);
//            if (CommonHelper.isNotBlank(attemptedAuth)) {
//                this.cleanAttemptedAuthentication(context);
//                this.cleanRequestedUrl(context);
//                //这里按自己需求处理，默认是返回了401，我在这边改为跳转到cas登录页面
//                //throw HttpAction.unauthorized(context);
//                return this.getRedirectActionBuilder().redirect(context);
//            } else {
//                return this.getRedirectActionBuilder().redirect(context);
//            }
//        }
    }

    private void cleanRequestedUrl(WebContext context) {
        SessionStore<WebContext> sessionStore = context.getSessionStore();
        if (sessionStore.get(context, Pac4jConstants.REQUESTED_URL) != null) {
            sessionStore.set(context, Pac4jConstants.REQUESTED_URL, "");
        }

    }

    private void cleanAttemptedAuthentication(WebContext context) {
        SessionStore<WebContext> sessionStore = context.getSessionStore();
        if (sessionStore.get(context, this.getName() + ATTEMPTED_AUTHENTICATION_SUFFIX) != null) {
            sessionStore.set(context, this.getName() + ATTEMPTED_AUTHENTICATION_SUFFIX, "");
        }

    }


}
