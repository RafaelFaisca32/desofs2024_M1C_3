import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Location from './location';
import LocationDetail from './location-detail';
import LocationUpdate from './location-update';
import LocationDeleteDialog from './location-delete-dialog';
import PrivateRoute from '../../shared/auth/private-route';
import { AUTHORITIES } from '../../config/constants';

const LocationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Location />} />
    <Route path="new" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.CUSTOMER]}>
        <LocationUpdate /> </PrivateRoute>} />
    <Route path=":id">
      <Route index element={<LocationDetail />} />
      <Route path="edit" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.CUSTOMER]}>
        <LocationUpdate /> </PrivateRoute>} />
        <Route path="delete" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.CUSTOMER]}>
        <LocationDeleteDialog /> </PrivateRoute>} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocationRoutes;
