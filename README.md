[![Build Status](https://travis-ci.org/coffeenet/coffeenet-starter.svg?branch=master)](https://travis-ci.org/coffeenet/coffeenet-starter)
[![Coverage Status](https://coveralls.io/repos/github/coffeenet/coffeenet-starter/badge.svg?branch=add-coveralls)](https://coveralls.io/github/coffeenet/coffeenet-starter?branch=add-coveralls)

# CoffeeNet Starters

These projects contains all CoffeeNet starters, which can be used
to integrate an application into the CoffeeNet.

[Security](coffeenet-starter-security/README.md)
provides the authentication against the CoffeeNet auth server.

[Service Discovery](./coffeenet-starter-discovery/README.md)
provides the integration to dustribute and search of CoffeeNet applications.

[Logging](./coffeenet-starter-logging/README.md)
provides a consistent and centralised logging.

[Navigation Thymleaf](./coffeenet-starter-navigation-thymeleaf/README.md)
provides the server side rendering of the navigation bar via thymeleaf.

[Navigation Javascript](./coffeenet-starter-navigation-javascript/README.md)
provides client side rendering of the navigation bar with javasript.

## Repository

```xml
<repositories>
  <repository>
    <id>releases.public.nexus.synyx.de</id>
    <url>http://nexus.synyx.de/content/repositories/public-releases</url>
  </repository>
</repositories>
```

## Release

To build a release from a `-SNAPSHOT` version:

- remove `-SNAPSHOT`
```bash
mvn org.codehaus.mojo:versions-maven-plugin:2.5:set -DremoveSnapshot=true -DprocessAllModules=true org.codehaus.mojo:versions-maven-plugin:2.5:commit
```

- commit and push with tag

- set new `-SNAPSHOT` version
```bash
mvn -f coffeenet-starter-parent/pom.xml org.codehaus.mojo:versions-maven-plugin:2.5:set -DnextSnapshot -DprocessAllModules=true
mvn org.codehaus.mojo:versions-maven-plugin:2.5:commit
```

- commit and push