set :application, 'deployments'
set :context_root, 'deployments'

set :tomcat_http_port, 30069
set :tomcat_shutdown_port, 31069
set :tomcat_java_opts, '-Xms512m -Xmx2048m'
set :tomcat_version, '6.0.26'
