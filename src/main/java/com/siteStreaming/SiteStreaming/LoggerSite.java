package com.siteStreaming.SiteStreaming;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Classe de logger pour avoir les informations après déploiement
 */
public class LoggerSite
{
    public static final Logger logger = Logger.getLogger(LoggerSite.class);

    public static void main(String[] args)
    {

        logger.debug("Log4j appender configuration is successful !!");
    }
}