
package info.freelibrary.maven;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sets the logging level for Maven builds to WARN instead of INFO. The only other way to do this is to configure the
 * logging level for all Maven builds in <code>${MAVEN_HOME}/conf/logging/simplelogger.properties</code> (which is not
 * so user-friendly for the beginning or average Maven user).
 * <p/>
 * The plugin comes with defaults, but can also be configured like: <code>
     <plugin>
       <groupId>info.freelibrary</groupId>
       <artifactId>freelib-utils</artifactId>
       <version>${freelib.utils.version}</version>
       <executions>
         <execution>
           <id>set-level</id>
           <phase>validate</phase>
           <configuration>
             <myLevel>debug</myLevel>
             <myExcludedLoggerNames>
               <loggerName>org.apache.maven.cli.event.ExecutionEventLogger</loggerName>
               <loggerName>org.apache.maven.tools.plugin.scanner.DefaultMojoScanner</loggerName>
             </myExcludedLoggerNames>
             <myIncludedLoggerNames>
               <loggerName>org.apache.maven.cli.event.ExecutionEventLogger</loggerName>
               <loggerName>org.apache.maven.tools.plugin.scanner.DefaultMojoScanner</loggerName>
             </myIncludedLoggerNames>
           </configuration>
           <goals>
             <goal>configure-logging</goal>
           </goals>
         </execution>
       </executions>
     </plugin>
 * </code>
 *
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
@Mojo(name = "configure-logging")
public class MavenLoggingMojo extends AbstractMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(MavenLoggingMojo.class);

    /**
     * A list of names of loggers to be excluded from the log level adjustment.
     */
    @Parameter
    private List<String> myExcludedLoggerNames;

    /**
     * A list of names of loggers to be included in the log level adjustment.
     */
    @Parameter
    private List<String> myIncludedLoggerNames;

    /**
     * The log level to reset the Maven loggers too.
     */
    @Parameter(property = "level", defaultValue = "error")
    private String myLevel;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final int level = MavenUtils.getLevelIntCode(myLevel);

        if (LOGGER.isWarnEnabled() && level == 0) {
            LOGGER.warn("Supplied log level '{}' was unknown, setting level to 'ERROR'", myLevel);
        }

        MavenUtils.setLogLevels(level == 0 ? MavenUtils.ERROR_LOG_LEVEL : level, MavenUtils.getMavenLoggers());
    }
}
