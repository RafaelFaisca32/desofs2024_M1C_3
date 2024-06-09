import React, {  useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ILocation } from 'app/shared/model/location.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { getEntityByLoggedInUser as getEntityByLoggedInUser } from 'app/entities/customer/customer.reducer';
import { IServiceRequest } from 'app/shared/model/service-request.model';
import { getEntity, updateEntity, createEntity, reset } from './service-request.reducer';

export const ServiceRequestUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customer = useAppSelector(state => state.customer.entity);
  const serviceRequestEntity = useAppSelector(state => state.serviceRequest.entity);
  const loading = useAppSelector(state => state.serviceRequest.loading);
  const updating = useAppSelector(state => state.serviceRequest.updating);
  const updateSuccess = useAppSelector(state => state.serviceRequest.updateSuccess);

  const handleClose = () => {
    navigate('/service-request');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEntityByLoggedInUser());
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.totalWeightOfItems !== undefined && typeof values.totalWeightOfItems !== 'number') {
      values.totalWeightOfItems = Number(values.totalWeightOfItems);
    }
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...serviceRequestEntity,
      ...values,
      location: customer.locations.find(it => it.id.toString() === values.location?.toString()),
      customer: customer,
      status: (values.status !== values.statusInitial) ? {id:null, date:null, observations:null, status: values.status, serviceRequest:null} : null,
    };

    if (isNew) {
      entity.status = {id:null, date:null, observations:null, status: "PENDING", serviceRequest:null}
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
          ...serviceRequestEntity,
          date: convertDateTimeFromServer(serviceRequestEntity.date),
          location: serviceRequestEntity?.location?.id,
          customer: serviceRequestEntity?.customer?.id,
          status: serviceRequestEntity.status?.status,
          statusInitial: serviceRequestEntity.status?.status,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="truckMotionApp.serviceRequest.home.createOrEditLabel" data-cy="ServiceRequestCreateUpdateHeading">
            Create or edit a Service Request
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <ValidatedField label="Items" id="service-request-items" name="items" data-cy="items" type="text" />
              <ValidatedField label="Service Name" id="service-request-serviceName" name="serviceName" data-cy="serviceName" type="text" />
              <ValidatedField
                label="Total Weight Of Items"
                id="service-request-totalWeightOfItems"
                name="totalWeightOfItems"
                data-cy="totalWeightOfItems"
                type="text"
              />
              <ValidatedField label="Price" id="service-request-price" name="price" data-cy="price" type="text" />
              <ValidatedField
                label="Date"
                id="service-request-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="service-request-customer" name="customer" data-cy="customer" label="Customer" type="select" hidden={true}>
                <option value={customer.id} key={customer.id}>
                  {customer.company}
                </option>
              </ValidatedField>
              <ValidatedField id="service-request-location" name="location" data-cy="location" label="Location" type="select">
                <option value="" key="0" />
                {customer.locations
                  ? customer.locations.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.id}>
                      {otherEntity.coordX +"--"+otherEntity.coordY+"--"+ otherEntity.coordZ}
                    </option>
                  ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/service-request" replace color="info">
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

export default ServiceRequestUpdate;
