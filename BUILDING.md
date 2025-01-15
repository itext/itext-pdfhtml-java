To build **pdfHTML**, [Maven][1] must be installed.

Running install without a profile will generate the **pdfHTML** jar:
```bash
$ mvn clean install \
    -Dmaven.test.skip=true \
    -Dmaven.javadoc.failOnError=false \
    > >(tee mvn.log) 2> >(tee mvn-error.log >&2)
```

To run the tests, [Ghostscript][2] and [Imagemagick][3] must be installed.
```bash
$ mvn clean install \
    -Dmaven.test.failure.ignore=false \
    -DgsExec=$(which gs) \
    -DcompareExec=$(which compare) \
    -Dmaven.javadoc.failOnError=false \
    > >(tee mvn.log) 2> >(tee mvn-error.log >&2)
```

Starting from version 6.1.0 **pdfHtml** supports native image compilation using [GraalVM][4]. Follow the instructions at
[Getting started](https://www.graalvm.org/latest/getting-started/) to build your first native application out of java sources.

To run tests in native mode [GraalVM][4] for JDK 22 or higher must be installed and native profile must be used as follows
```bash
$ mvn clean install -Pnative -DskipTests=false \
    -Dmaven.test.failure.ignore=false \
    -DgsExec=$(which gs) \
    -DcompareExec=$(which compare) \
    -Dmaven.javadoc.failOnError=false \
    > >(tee mvn.log) 2> >(tee mvn-error.log >&2)
```

You can use the supplied `Vagrantfile` to get a [Vagrant][5] VM ([Ubuntu][6] 14.04 LTS - Trusty Tahr, with [VirtualBox][7]) with all the required software installed.
```bash
$ vagrant box add ubuntu/trusty64
$ vagrant up
$ vagrant ssh -- \
    'cd /vagrant ; mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.failOnError=false' \
    > >(tee mvn.log) 2> >(tee mvn-error.log >&2)
```

[1]: http://maven.apache.org/
[2]: http://www.ghostscript.com/
[3]: http://www.imagemagick.org/
[4]: https://www.graalvm.org/
[5]: https://www.vagrantup.com/
[6]: http://www.ubuntu.com/
[7]: https://www.virtualbox.org/
