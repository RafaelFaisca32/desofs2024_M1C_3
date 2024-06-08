import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Truck from './truck';
import TruckDetail from './truck-detail';
import TruckUpdate from './truck-update';
import TruckDeleteDialog from './truck-delete-dialog';
import PrivateRoute from '../../shared/auth/private-route';
import { AUTHORITIES } from '../../config/constants';

const TruckRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Truck />} />
    <Route path="new" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.MANAGER]}>
        <TruckUpdate /> </PrivateRoute>} />
    <Route path=":id">
      <Route index element={<TruckDetail />} />
      <Route path="edit" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.MANAGER]}>
        <TruckUpdate /> </PrivateRoute>} />
        <Route path="delete" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.MANAGER]}>
        <TruckDeleteDialog /> </PrivateRoute>} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TruckRoutes;
