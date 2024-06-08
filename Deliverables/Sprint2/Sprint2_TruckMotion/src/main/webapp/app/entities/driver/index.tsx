import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Driver from './driver';
import DriverDetail from './driver-detail';
import DriverUpdate from './driver-update';
import DriverDeleteDialog from './driver-delete-dialog';

const DriverRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Driver />} />
    <Route path=":id">
      <Route index element={<DriverDetail />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DriverRoutes;
