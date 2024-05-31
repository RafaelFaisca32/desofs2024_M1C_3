import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceStatus from './service-status';
import ServiceStatusDetail from './service-status-detail';
import ServiceStatusUpdate from './service-status-update';
import ServiceStatusDeleteDialog from './service-status-delete-dialog';

const ServiceStatusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceStatus />} />
    <Route path="new" element={<ServiceStatusUpdate />} />
    <Route path=":id">
      <Route index element={<ServiceStatusDetail />} />
      <Route path="edit" element={<ServiceStatusUpdate />} />
      <Route path="delete" element={<ServiceStatusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceStatusRoutes;
