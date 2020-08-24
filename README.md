Gatling-performance-tests
=========================

The implementation is dedicated for performance testing of stores based on Magento 2 e-commerce platform, but you can customize it and use for your purposes as well.
Thanks to the gatling maven plugin it is fully parametrized, and easy to use e.g. in CI via Jenkins/Travis.

To test it out, execute command with sample parametrization:

    mvn gatling:test -Dgatling.simulationClass=simulations.MainSimulation -DBASE_URL=http://sample-url.com -DUSERS=50 -DRAMP_DURATION=120 -DDURATION=10


