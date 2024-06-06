import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { Row, Col, Button } from 'reactstrap';
import { toast } from 'react-toastify';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { savePassword, reset } from './password.reducer';

export const PasswordPage = () => {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(reset());
    dispatch(getSession());
    return () => {
      dispatch(reset());
    };
  }, []);

  const handleValidSubmit = ({ currentPassword, confirmPassword, newPassword }) => {
    const transformedFirstPassword = transformPassword(newPassword);
    const transformedSecondPassword = transformPassword(confirmPassword);
    if (transformedFirstPassword !== transformedSecondPassword) {
      toast.error("The password and its confirmation do not match!");
      return;
    }
    dispatch(savePassword({ currentPassword, newPassword }));
  };

  const transformPassword = (password) => {
    return password.replace(/\s{2,}/g, ' ');
  };

  const updatePassword = event => {
    const transformedPassword = transformPassword(event.target.value);
    setPassword(transformedPassword);
  };

  const updateConfirmPassword = event => {
    const transformedPassword = transformPassword(event.target.value);
    setConfirmPassword(transformedPassword);
  };

  const toggleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const toggleShowConfirmPassword = () => {
    setShowConfirmPassword(!showConfirmPassword);
  };

  const account = useAppSelector(state => state.authentication.account);
  const successMessage = useAppSelector(state => state.password.successMessage);
  const errorMessage = useAppSelector(state => state.password.errorMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    } else if (errorMessage) {
      toast.error(errorMessage);
    }
    dispatch(reset());
  }, [successMessage, errorMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="password-title">
            Password for [<strong>{account.login}</strong>]
          </h2>
          <ValidatedForm id="password-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="currentPassword"
              label="Current password"
              placeholder="Current password"
              type="password"
              validate={{
                required: { value: true, message: 'Your password is required.' },
              }}
              data-cy="currentPassword"
            />
            <Row className="align-items-center">
              <Col>
                <ValidatedField
                  name="newPassword"
                  placeholder="New password"
                  type={showPassword ? 'text' : 'password'}
                  value={password}
                  onChange={updatePassword}
                  validate={{
                    required: { value: true, message: 'Your password is required.' },
                    minLength: { value: 12, message: 'Your password is required to be at least 12 characters.' },
                    maxLength: { value: 128, message: 'Your password cannot be longer than 128 characters.' },
                  }}
                  data-cy="newPassword"
                />
              </Col>
              <Col>
                <Button color="info" onClick={toggleShowPassword} className="ml-2">
                  {showPassword ? 'Hide' : 'Show'}
                </Button>
              </Col>
            </Row>
            <PasswordStrengthBar password={password} />
            <Row className="align-items-center">
              <Col>
                <ValidatedField
                  name="confirmPassword"
                  placeholder="Confirm the new password"
                  type={showConfirmPassword ? 'text' : 'password'}
                  value={confirmPassword}
                  onChange={updateConfirmPassword}
                  validate={{
                    required: { value: true, message: 'Your confirmation password is required.' },
                    minLength: { value: 12, message: 'Your confirmation password is required to be at least 12 characters.' },
                    maxLength: { value: 128, message: 'Your confirmation password cannot be longer than 128 characters.' },
                    validate: v => v === password || 'The password and its confirmation do not match!',
                  }}
                  data-cy="confirmPassword"
                />
              </Col>
              <Col>
                <Button color="info" onClick={toggleShowConfirmPassword} className="ml-2">
                  {showConfirmPassword ? 'Hide' : 'Show'}
                </Button>
              </Col>
            </Row>
            <Button color="success" type="submit" data-cy="submit" className="mt-3">
              Save
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordPage;
