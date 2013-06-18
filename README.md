DONE 1 - SVN Kit indexer to scan {repository} (bt-dso) and find paths matching /config/deploy/stages etc
        - Nightly filesystem scan of SVN dump to create indexes
DONE 2 - Service scanning SVN index locations (1) for diff, and then triggering 3
DONE 3 - Service to turn SVN deploy config into {com.bt.tools.deployments.domain.Project}
        - X time http scan to look for differences (change in SVN revision)
DONE 4 - List of existing:
        - App servers
        - DB servers
5 - Service API to update real capabilities (3)
6 - Config validation service
    DONE - Does app server exist?
    DONE - Does DB server exist?
    DONE - Are DB credentials valid?
    - Is project running (http GET)?
    - Are ports valid
    - etc
7 - Diagram view of this data. XML > PNG

TODO:
- Use threadsafe persistence layer
