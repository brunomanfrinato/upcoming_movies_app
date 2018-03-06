package com.brunomanfrinato.upcomingmoviesapp.exception;

import java.io.IOException;

/**
 * Created by brunomanfrinato on 04/03/18.
 */

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity exception";
    }

}
