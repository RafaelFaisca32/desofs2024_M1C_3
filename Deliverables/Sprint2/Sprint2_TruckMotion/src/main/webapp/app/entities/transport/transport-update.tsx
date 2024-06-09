import React, {  useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {  ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { getEntities as getDrivers } from 'app/entities/driver/driver.reducer';
import { getEntities as getServiceRequests } from 'app/entities/service-request/service-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './transport.reducer';

export const TransportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locations = useAppSelector(state => state.location.entities);
  const drivers = useAppSelector(state => state.driver.entities);
  const serviceRequests = useAppSelector(state => state.serviceRequest.entities);
  const transportEntity = useAppSelector(state => state.transport.entity);
  const loading = useAppSelector(state => state.transport.loading);
  const updating = useAppSelector(state => state.transport.updating);
  const updateSuccess = useAppSelector(state => state.transport.updateSuccess);

  const handleClose = () => {
    navigate('/transport');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLocations({}));
    dispatch(getDrivers({}));
    dispatch(getServiceRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    values.startTime = convertDateTimeToServer(values.startTime);
    values.endTime = convertDateTimeToServer(values.endTime);

    const entity = {
      ...transportEntity,
      ...values,
      location: locations.find(it => it.id.toString() === values.location?.toString()),
      driver: drivers.find(it => it.id.toString() === values.driver?.toString()),
      serviceRequest: serviceRequests.find(it => it.id.toString() === values.serviceRequest?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startTime: displayDefaultDateTime(),
          endTime: displayDefaultDateTime(),
        }
      : {
          ...transportEntity,
          startTime: convertDateTimeFromServer(transportEntity.startTime),
          endTime: convertDateTimeFromServer(transportEntity.endTime),
          location: transportEntity?.location?.id,
          driver: transportEntity?.driver?.id,
          serviceRequest: transportEntity?.serviceRequest?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="truckMotionApp.transport.home.createOrEditLabel" data-cy="TransportCreateUpdateHeading">
            Create or edit a Transport
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <ValidatedField
                label="Start Time"
                id="transport-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="End Time"
                id="transport-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/transport" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TransportUpdate;
