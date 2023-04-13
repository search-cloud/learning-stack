# 学习 nginx Learning Nginx

### Install

```bash
yum install -y httpd-devel pcre perl pcre-devel zlib zlib-devel GeoIP GeoIP-devel
```

unzip:
```bash
tar -xvf zlib-1.2.11.tar.gz
tar -xvf pcre-8.42.tar.gz
tar -xvf openssl-1.0.2q.tar.gz
```

install gcc g++:
```bash
yum install gcc gcc-c++
```

configure:
```bash
./configure --user=nginx --group=nginx \
--prefix=/usr/share/nginx \
--sbin-path=/usr/sbin/nginx \
--conf-path=/etc/nginx/nginx.conf \
--error-log-path=/var/log/nginx/error.log \
--http-log-path=/var/log/nginx/access.log \
--http-client-body-temp-path=/var/lib/nginx/tmp/client_body \
--http-proxy-temp-path=/var/lib/nginx/tmp/proxy \
--http-fastcgi-temp-path=/var/lib/nginx/tmp/fastcgi \
--pid-path=/var/run/nginx.pid \
--lock-path=/var/lock/subsys/nginx --with-http_ssl_module \
--with-http_realip_module --with-http_addition_module \
--with-http_sub_module --with-http_dav_module \
--with-http_flv_module --with-http_gzip_static_module \
--with-http_stub_status_module \
--with-mail --with-mail_ssl_module \
--with-cc-opt="-I /usr/local/include" \
--with-openssl=/home/vincentlu/openssl-1.0.2q \
--with-pcre --with-pcre=/home/vincentlu/pcre-8.42 \
--with-zlib=/home/vincentlu/zlib-1.2.11 --with-http_geoip_module
```

```log
Configuration summary
  + using PCRE library: /home/vincentlu/pcre-8.42
  + using OpenSSL library: /home/vincentlu/openssl-1.0.2q
  + using zlib library: /home/vincentlu/zlib-1.2.11

  nginx path prefix: "/usr/share/nginx"
  nginx binary file: "/usr/sbin/nginx"
  nginx modules path: "/usr/share/nginx/modules"
  nginx configuration prefix: "/etc/nginx"
  nginx configuration file: "/etc/nginx/nginx.conf"
  nginx pid file: "/var/run/nginx.pid"
  nginx error log file: "/var/log/nginx/error.log"
  nginx http access log file: "/var/log/nginx/access.log"
  nginx http client request body temporary files: "/var/lib/nginx/tmp/client_body"
  nginx http proxy temporary files: "/var/lib/nginx/tmp/proxy"
  nginx http fastcgi temporary files: "/var/lib/nginx/tmp/fastcgi"
  nginx http uwsgi temporary files: "uwsgi_temp"
  nginx http scgi temporary files: "scgi_temp"
```
