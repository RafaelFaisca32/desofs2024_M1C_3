import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Driver from './driver';
import Manager from './manager';
import Customer from './customer';
import Truck from './truck';
import Location from './location';
import ServiceRequest from './service-request';
import ServiceStatus from './service-status';
import Transport from './transport';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="driver/*" element={<Driver />} />
        <Route path="manager/*" element={<Manager />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="truck/*" element={<Truck />} />
        <Route path="location/*" element={<Location />} />
        <Route path="service-request/*" element={<ServiceRequest />} />
        <Route path="service-status/*" element={<ServiceStatus />} />
        <Route path="transport/*" element={<Transport />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
