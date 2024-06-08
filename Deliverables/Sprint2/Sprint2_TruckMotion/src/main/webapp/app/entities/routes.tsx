import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Driver from './driver';
import Manager from './manager';
import Customer from './customer';
import ApplicationUser from './application-user';
import Truck from './truck';
import Location from './location';
import ServiceRequest from './service-request';
import ServiceStatus from './service-status';
import Transport from './transport';
import PrivateRoute from '../shared/auth/private-route';

import { AUTHORITIES } from '../config/constants';
/* jhipster-needle-add-route-import - JHipster will add routes here */



export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        <Route path="driver/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.MANAGER]}>
        <Driver /> </PrivateRoute>} />
        <Route path="manager/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.MANAGER]}>
        <Manager /> </PrivateRoute>} />
        <Route path="customer/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.MANAGER]}>
        <Customer /> </PrivateRoute>} />
        <Route path="truck/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.MANAGER, AUTHORITIES.DRIVER]}>
        <Truck /> </PrivateRoute>} /> 
        <Route path="location/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.MANAGER, AUTHORITIES.CUSTOMER
          ]}>
        <Location /> </PrivateRoute>} /> 
        <Route path="service-request/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.CUSTOMER, AUTHORITIES.MANAGER]}>
        <ServiceRequest /> </PrivateRoute>} /> 
        <Route path="service-status/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
        <ServiceStatus /> </PrivateRoute>} /> 
        <Route path="transport/*" element={
          <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.DRIVER]}>
        <Transport /> </PrivateRoute>} /> 

        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
