import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { AUTHORITIES } from 'app/config/constants';
import {getUser, getRoles, updateUser, createUser, reset, fetchFreeTrucks} from './user-management.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {ICompleteUser, IUser} from "app/shared/model/user.model";
import {IApplicationUser} from "app/shared/model/application-user.model";
import {IManager} from "app/shared/model/manager.model";
import {IDriver} from "app/shared/model/driver.model";
import {ICustomer} from "app/shared/model/customer.model";

export const UserManagementUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { login } = useParams<'login'>();
  const isNew = login === undefined;

  const [selectedRoles, setSelectedRoles] = useState([])
  const [trucks, setTrucks] = useState([]);

  const handleRolesChange = (event) =>{
    const options = event.target.options;
    const selectedValues = [];
    for (let i = 0; i < options.length; i++) {
      if (options[i].selected) {
        selectedValues.push(options[i].value);
      }
    }
    setSelectedRoles(selectedValues);

  }

  const isRoleSelected = (role: string) => !!selectedRoles.find(element => element === role);

  const validateRoleOption = (role: string) => AUTHORITIES.ADMIN != role || (AUTHORITIES.ADMIN == role && authoritiesLogin.includes(AUTHORITIES.ADMIN));

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getUser(login));
    }
    dispatch(getRoles());
    dispatch(fetchFreeTrucks())
      .then((response: any) => {
        setTrucks(response.payload);
      })
    .catch((error: any) => {
      setTrucks([])
    });
    return () => {
      dispatch(reset());
    };
  }, [login]);

  const handleClose = () => {
    navigate('/admin/user-management');
  };

  const saveUser = values => {


    if (isNew) {
      const user: IUser = {
        id: values.id,
        login: values.login,
        firstName: values.firstName,
        lastName: values.lastName,
        email: values.email,
        activated: values.activated,
        langKey: values.langKey === "" ? null : values.langKey,
        authorities: values.authorities,
        createdBy: values.createdBy,
        createdDate: values.createdDate,
        lastModifiedBy: values.lastModifiedBy,
        lastModifiedDate: values.lastModifiedDate,
        password: values.password
      }
      const applicationUser: IApplicationUser = {
        id: values.id,
        birthDate: values.birthDate,
        gender: values.gender
      }

      let manager : IManager = null;
      if( values.authorities.find(x => x === AUTHORITIES.MANAGER) ){
        manager = {};
      }
      let driver : IDriver = null;
      if(values.authorities.find(x => x === AUTHORITIES.DRIVER)){
        driver = {
          truck: { id: values.truck }
        }
      }
      let customer : ICustomer = null;
      if(values.authorities.find(x => x === AUTHORITIES.CUSTOMER)){
        customer = {
          company: values.company
        }
      }

      const completeUser : ICompleteUser = {
        userDTO: user,
        applicationUserDTO: applicationUser,
        managerDTO: manager,
        driverDTO: driver,
        customerDTO: customer
      }
      dispatch(createUser(completeUser));
    }

    handleClose();
  };

  const isInvalid = false;
  const account = useAppSelector(state => state.authentication.account);
  const authoritiesLogin = account.authorities as string[];
  const user = useAppSelector(state => state.userManagement.user);
  const loading = useAppSelector(state => state.userManagement.loading);
  const updating = useAppSelector(state => state.userManagement.updating);
  const authorities = useAppSelector(state => state.userManagement.authorities);
  const genderValues = Object.keys(Gender);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          {isNew ? (<h1>Create a user</h1>) : (<h1>User</h1>) }
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm onSubmit={saveUser} defaultValues={user}>
              {user.id ? <ValidatedField type="text" name="id" required hidden={true} readOnly label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                type="text"
                name="login"
                label="Login"
                validate={{
                  required: {
                    value: true,
                    message: 'Your username is required.',
                  },
                  pattern: {
                    value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                    message: 'Your username is invalid.',
                  },
                  minLength: {
                    value: 1,
                    message: 'Your username is required to be at least 1 character.',
                  },
                  maxLength: {
                    value: 50,
                    message: 'Your username cannot be longer than 50 characters.',
                  },
                }}
              />
              <ValidatedField
                type="text"
                name="firstName"
                label="First name"
                validate={{
                  maxLength: {
                    value: 50,
                    message: 'This field cannot be longer than 50 characters.',
                  },
                }}
              />
              <ValidatedField
                type="text"
                name="lastName"
                label="Last name"
                validate={{
                  maxLength: {
                    value: 50,
                    message: 'This field cannot be longer than 50 characters.',
                  },
                }}
              />
              <FormText>This field cannot be longer than 50 characters.</FormText>
              <ValidatedField
                name="email"
                label="Email"
                placeholder="Your email"
                type="email"
                validate={{
                  required: {
                    value: true,
                    message: 'Your email is required.',
                  },
                  minLength: {
                    value: 5,
                    message: 'Your email is required to be at least 5 characters.',
                  },
                  maxLength: {
                    value: 254,
                    message: 'Your email cannot be longer than 50 characters.',
                  },
                  validate: v => isEmail(v) || 'Your email is invalid.',
                }}
              />
              <ValidatedField label="Birth Date" id="application-user-birthDate" name="birthDate" data-cy="birthDate" type="date" />
              <ValidatedField label="Gender" id="application-user-gender" name="gender" data-cy="gender" type="select">
                {genderValues.map(gender => (
                  <option value={gender} key={gender}>
                    {gender}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField type="checkbox" name="activated" check value={true} disabled={!user.id} label="Activated" />
              <ValidatedField type="select" name="authorities" multiple label="Profiles" onChange={handleRolesChange}>
                {authorities.map(role => {
                  const shouldRenderOption = validateRoleOption(role);
                  if (shouldRenderOption) {
                    return (
                      <option value={role} key={role}>
                        {role}
                      </option>
                    );
                  } else {
                    return null; // Return null if the condition is not met
                  }
                })}
              </ValidatedField>
              { isRoleSelected(AUTHORITIES.MANAGER) && null}
              { isRoleSelected(AUTHORITIES.DRIVER) && (
                <ValidatedField id="driver-truck" name="truck" data-cy="truck" label="Truck" type="select">
                  <option value="" key="0" />
                  {trucks
                    ? trucks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.make + " " + otherEntity.model}
                      </option>
                    ))
                    : null}
                </ValidatedField>
              )}
              { isRoleSelected(AUTHORITIES.CUSTOMER) && (
                <ValidatedField label="Company" id="customer-company" name="company" data-cy="company" type="text" />
              )}
              <Button tag={Link} to="/admin/user-management" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              { isNew &&
                (
                <Button color="primary" type="submit" disabled={isInvalid || updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
                )
              }
            </ValidatedForm>

          )}
        </Col>
      </Row>
    </div>
  );
};

export default UserManagementUpdate;
