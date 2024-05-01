import driver from 'app/entities/driver/driver.reducer';
import manager from 'app/entities/manager/manager.reducer';
import customer from 'app/entities/customer/customer.reducer';
import truck from 'app/entities/truck/truck.reducer';
import location from 'app/entities/location/location.reducer';
import service from 'app/entities/service/service.reducer';
import serviceStatus from 'app/entities/service-status/service-status.reducer';
import transport from 'app/entities/transport/transport.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  driver,
  manager,
  customer,
  truck,
  location,
  service,
  serviceStatus,
  transport,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
