## TODO (move to issues)


* SVN Kit indexer to scan {repository} (bt-dso) and find paths matching /config/deploy/stages etc
** Nightly filesystem scan of SVN dump to create indexes
* Service scanning SVN index locations (1) for diff, and then triggering 3
* Service to turn SVN deploy config into {com.bt.tools.deployments.domain.Project}
** X time http scan to look for differences (change in SVN revision)
* List of existing:
** App servers
** DB servers
* Service API to update real capabilities (3)
* Config validation service
** Does app server exist?
** Does DB server exist?
** Are DB credentials valid?
** Is project running (http GET)?
** Are ports valid
** etc
* Diagram view of this data. XML > PNG
* Use threadsafe persistence layer
