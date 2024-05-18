import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IServiceRequest } from 'app/shared/model/service-request.model';
import { getEntities as getServiceRequests } from 'app/entities/service-request/service-request.reducer';
import { IServiceStatus } from 'app/shared/model/service-status.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { getEntity, updateEntity, createEntity, reset } from './service-status.reducer';

export const ServiceStatusUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const serviceRequests = useAppSelector(state => state.serviceRequest.entities);
  const serviceStatusEntity = useAppSelector(state => state.serviceStatus.entity);
  const loading = useAppSelector(state => state.serviceStatus.loading);
  const updating = useAppSelector(state => state.serviceStatus.updating);
  const updateSuccess = useAppSelector(state => state.serviceStatus.updateSuccess);
  const statusValues = Object.keys(Status);

  const handleClose = () => {
    navigate('/service-status');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getServiceRequests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...serviceStatusEntity,
      ...values,
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
          date: displayDefaultDateTime(),
        }
      : {
          status: 'ACTIVE',
          ...serviceStatusEntity,
          date: convertDateTimeFromServer(serviceStatusEntity.date),
          serviceRequest: serviceStatusEntity?.serviceRequest?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="truckMotionApp.serviceStatus.home.createOrEditLabel" data-cy="ServiceStatusCreateUpdateHeading">
            Create or edit a Service Status
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="service-status-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Date"
                id="service-status-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Observations"
                id="service-status-observations"
                name="observations"
                data-cy="observations"
                type="text"
              />
              <ValidatedField label="Status" id="service-status-status" name="status" data-cy="status" type="select">
                {statusValues.map(status => (
                  <option value={status} key={status}>
                    {status}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="service-status-serviceRequest"
                name="serviceRequest"
                data-cy="serviceRequest"
                label="Service Request"
                type="select"
              >
                <option value="" key="0" />
                {serviceRequests
                  ? serviceRequests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/service-status" replace color="info">
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

export default ServiceStatusUpdate;
