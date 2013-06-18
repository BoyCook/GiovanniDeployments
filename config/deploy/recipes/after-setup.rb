app_dir = "/var/lib/#{application}"
data_dir = "#{app_dir}/data"

after 'deploy:update_code' do
  sudo "rm -f #{data_dir}/*.data"
end

after 'deploy:setup' do
  sudo "mkdir -p #{data_dir}"
  permissions.normalise data_dir, :owner => application, :group => 'tomcat'
end
