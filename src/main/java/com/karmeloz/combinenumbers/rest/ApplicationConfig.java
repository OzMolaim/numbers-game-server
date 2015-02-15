
package com.karmeloz.combinenumbers.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(WelcomeResource.class);
        resources.add(ChallengeResource.class);
        resources.add(FriendDifficultyLevelResource.class);
        resources.add(UserResource.class);
        resources.add(DevelopmentResource.class);
        return resources;
    }
}
