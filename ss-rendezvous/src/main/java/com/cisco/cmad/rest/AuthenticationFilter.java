package com.cisco.cmad.rest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.User;
import com.cisco.cmad.api.UserNotFoundException;
import com.cisco.cmad.biz.SimpleRendezvous;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * This filter verify the access permissions for a user based on username and
 * passowrd provided in request
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

	public static final String AUTHORIZATION_PROPERTY = "Authorization";
	public static final String AUTHENTICATION_SCHEME = "Bearer";
	private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
			.entity("You cannot access this resource").build();
	private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
			.entity("Access blocked for all users !!").build();

	public static final String key = "rendezvous";

	@Override
	public void filter(ContainerRequestContext requestContext) {
		Method method = resourceInfo.getResourceMethod();
		// Access allowed for all
		if (!method.isAnnotationPresent(PermitAll.class)) {
			// Access denied for all
			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(ACCESS_FORBIDDEN);
				return;
			}

			// Get request headers
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();

			// Fetch authorization header
			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			// If no authorization information present; block access
			if (authorization == null || authorization.isEmpty()) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}

			// Get encoded username and password
			final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			// Decode username and password
			// String usernameAndPassword = new
			// String(Base64.decode(encodedUserPassword.getBytes()));

			// Split username and password tokens
			// final StringTokenizer tokenizer = new
			// StringTokenizer(usernameAndPassword, ":");
			// final String username = tokenizer.nextToken();
			// final String password = tokenizer.nextToken();
			Jws<Claims> claims = null;
			try {
				claims = Jwts.parser().setSigningKey(key).parseClaimsJws(encodedUserPassword);
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
					| IllegalArgumentException e) {
				e.printStackTrace();
			}

			String username = claims.getBody().getSubject();

			// Is user valid?
			if (!isUserAllowed(username)) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}
		}
	}

	private boolean isUserAllowed(final String username) {
		boolean isAllowed = false;

		Rendezvous rendezvous = new SimpleRendezvous();
		User user = null;
		try {
			user = rendezvous.getUserByUsername(username);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return isAllowed;
		} catch (RendezvousException e) {
			e.printStackTrace();
			return isAllowed;
		}

		if (user.getUsername().equals(username))
			isAllowed = true;
		return isAllowed;
	}
}