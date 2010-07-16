/**
 * 
 */
package info.freelibrary.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 * @goal jar
 */
public class JarClassMojo extends AbstractMojo {

	private static final String FILE_SEP = System.getProperty("file.separator");

	/**
	 * @parameter expression="${plugin.version}"
	 */
	private String myVersion;

	/**
	 * @parameter expression="${plugin.groupId}"
	 */
	private String myGroupId;

	/**
	 * @parameter expression="${plugin.artifactId}"
	 */
	private String myArtifactId;

	/**
	 * @parameter expression="${settings.localRepository}"
	 */
	private String myRepo;

	/**
	 * @parameter
	 */
	private List<String> myClasses;

	/**
	 * @parameter expression="${project.build.outputDirectory}"
	 */
	private String myDest;

	/**
	 * 
	 */
	public void execute() throws MojoExecutionException {
		String jarFileName = myArtifactId + "-" + myVersion + ".jar";
		StringBuilder jarPath = new StringBuilder();

		jarPath.append(myRepo).append(FILE_SEP);
		jarPath.append(myGroupId.replace('.', '/')).append(FILE_SEP);
		jarPath.append(myArtifactId).append(FILE_SEP);
		jarPath.append(myVersion).append(FILE_SEP).append(jarFileName);

		try {
			JarFile jarFile = new JarFile(jarPath.toString());
			Enumeration<JarEntry> entries = jarFile.entries();

			while (entries.hasMoreElements()) {
				Iterator<String> iterator = myClasses.iterator();
				JarEntry entry = entries.nextElement();
				String entryName = entry.getName();

				while (iterator.hasNext()) {
					String name = iterator.next();

					if (entryName.equals(name)) {
						InputStream in = jarFile.getInputStream(entry);
						BufferedInputStream bIn = new BufferedInputStream(in);
						byte[] bytes = new byte[(int) entry.getSize()];
						BufferedOutputStream bOut = getOutputStream(name);

						bIn.read(bytes);
						bOut.write(bytes);
						
						bOut.close();
						bIn.close();
					}
				}
			}
		}
		catch (IOException details) {
			throw new MojoExecutionException(details.getMessage(), details);
		}
	}

	private BufferedOutputStream getOutputStream(String aFileName)
			throws FileNotFoundException, IOException {
		StringTokenizer tokenizer = new StringTokenizer(aFileName, "/");
		BufferedOutputStream bOut = null;
		File target = new File(myDest);

		while (tokenizer.hasMoreTokens()) {
			String dirName = tokenizer.nextToken();

			if (tokenizer.hasMoreTokens()) {
				File dir = new File(target, dirName);

				if (!dir.exists()) {
					if (!dir.mkdir()) {
						throw new IOException("Couldn't create "
								+ dir.getAbsolutePath());
					}
				}

				target = dir;
			}
			else {
				File file = new File(target, dirName);
				FileOutputStream outStream = new FileOutputStream(file);
				bOut = new BufferedOutputStream(outStream);
			}
		}

		return bOut;
	}
}
