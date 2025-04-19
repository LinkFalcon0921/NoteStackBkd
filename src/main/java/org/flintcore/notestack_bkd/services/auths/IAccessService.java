package org.flintcore.notestack_bkd.services.auths;

import org.flintcore.notestack_bkd.dtos.responses.TokenAuthResponse;

/**
 *
 * Evaluate the credentials of the given entities.
 *
 * @param <A> Entity for <i>Authentication</i>.
 * @param <R> Entity for <i>Registration</i>.
 * */
public interface IAccessService<A, R> {
    TokenAuthResponse register(R r);
    TokenAuthResponse authenticate(A a);
}
