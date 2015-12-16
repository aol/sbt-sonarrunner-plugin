publish local
    `setJdk6`
    `sbt publishLocal`

test

    `cd sonarrunner-test-project`
    `sbt sonar -Dplugin.version=XXXXX`

tag

    `sbt clean publish`

release

    `sbt release`
