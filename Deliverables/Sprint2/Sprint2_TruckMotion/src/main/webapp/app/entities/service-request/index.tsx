import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ServiceRequest from './service-request';
import ServiceRequestDetail from './service-request-detail';
import ServiceRequestUpdate from './service-request-update';
import { ServiceRequestStatusUpdate } from './server-request-status-update';
import PrivateRoute from '../../shared/auth/private-route';
import { AUTHORITIES } from '../../config/constants';

const ServiceRequestRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ServiceRequest />} />
    <Route path="new" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.CUSTOMER]}>
        <ServiceRequestUpdate /> </PrivateRoute>} />
    <Route path=":id">
      <Route index element={<ServiceRequestDetail />} />
      <Route path="edit" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.CUSTOMER]}>
        <ServiceRequestUpdate /> </PrivateRoute>} />
      <Route path="statusUpdate" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.MANAGER]}>
        <ServiceRequestStatusUpdate></ServiceRequestStatusUpdate> </PrivateRoute>} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServiceRequestRoutes;
