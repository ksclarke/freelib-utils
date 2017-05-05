title=Welcome to the FreeLib-Utils Project
date=2013-09-24
type=page
status=published
~~~~~~

This project is a small collection of convenience and utility classes, mostly used by other FreeLibrary projects. It includes utilities related to I18N, I/O, ResourceBundles, String manipulation, XQuery, ClassLoaders, and Pairtree file systems.

<script>
xmlhttp=new XMLHttpRequest();
xmlhttp.open("GET", "https://55rkvqxzlf.execute-api.us-east-1.amazonaws.com/maven?q=freelib-utils", false);
xmlhttp.send();
$version = xmlhttp.responseText;
</script>

## Using FreeLib-Utils

To use the FreeLib-Utils library, reference it in your project's `pom.xml` file.

<pre><code>&lt;dependency&gt;
  &lt;groupId&gt;info.freelibrary&lt;/groupId&gt;
  &lt;artifactId&gt;freelib-utils&lt;/artifactId&gt;
  &lt;version&gt;<script>document.write($version);</script><noscript>${version}</noscript>&lt;/version&gt;
&lt;/dependency&gt;
</code></pre>

<br/>Or, to use it with Gradle/Grails, include the following in your project's `build.gradle` file:

<pre><code>compile &apos;info.freelibrary:freelib-utils:<script>
document.write($version);</script><noscript>${version}</noscript>&apos;</code></pre>
<p/>
## Building FreeLib-Utils

If you'd like to build the project yourself, you'll need a current <a href="http://openjdk.java.net/" target="_blank">JDK</a> and <a href="https://maven.apache.org/" target="_blank">Maven</a> installed and added to your system path.  You can then download a [stable release](https://github.com/ksclarke/freelib-utils/releases) or clone the project using <a href="http://git-scm.com" target="_blank">Git</a>. To clone the project, type:

    git clone https://github.com/ksclarke/freelib-utils.git
    cd freelib-utils

<br/>To build the project, type:

    mvn install

<br/>To build the project's documentation, type:

    mvn javadoc:javadoc

<br/>For more information, consult the "Docs" dropdown in the navigation menu at the top of the page.
