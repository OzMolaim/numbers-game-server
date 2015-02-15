package com.karmeloz.combinenumbers.rest;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.karmeloz.combinenumbers.dao.interfaces.ChallengeDao;
import com.karmeloz.combinenumbers.dao.interfaces.UserDataDao;
import com.karmeloz.combinenumbers.dao.interfaces.VisitDao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Stateless
@Path("/development")
public class DevelopmentResource {

    @EJB
    private UserDataDao userDataDao;

    @EJB
    private ChallengeDao challengeDao;

    @EJB
    private VisitDao visitDao;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream index() {
        return getClass().getResourceAsStream("index.html");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/reset")
    public String resetAllData(@QueryParam("skipUsers") Boolean skipUsers) {
        visitDao.deleteAll();
        challengeDao.deleteAll();
        if (!Boolean.TRUE.equals(skipUsers)) {
            userDataDao.deleteAll();
        }
        return "OK!";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/log")
    public String displayLog() throws IOException {
        File logFile = new File("/temp/ctn.log");
        return Files.toString(logFile, Charsets.UTF_8);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/delete_log")
    public String truncateLog() throws IOException {
        File log = new File("/temp/ctn.log");
        new FileOutputStream(log).getChannel().truncate(0).close();
        return "Done!";
    }
}
