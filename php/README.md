# PHP

In order to perform the kata, first of all you will need to install all of the dependencies. This can be done using
composer (standing from the *"php"* directory")
```shell
wget http://getcomposer.org/composer.phar
php composer.phar install
```

Next, to execute the unit tests you need run this from the *php* directory 
```shell
./vendor/bin/phpunit
```
or

you could use composer for this
```shell
composer test
```

## Coverage

To run with coverage you will have to configure your system with this:

Linux and macOS
```shell
export XDEBUG_MODE=coverage
```

Windows
```shell
set XDEBUG_MODE=coverage
```

When running the tests a coverage report should be generated automatically in simple text format and html report. If you want
to visualize it from the browser you can open the `coverage/report/index.html` file in a browser after running the tests.

Enjoy
