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

  // eslint-disable-next-line @typescript-eslint/no-shadow
  const handleValidSubmit = ({ currentPassword, confirmPassword, newPassword }) => {
    const transformedFirstPassword = transformPassword(newPassword);
    const transformedSecondPassword = transformPassword(confirmPassword);
    if (transformedFirstPassword !== transformedSecondPassword) {
      toast.error("The password and its confirmation do not match!");
      return;
    }
    dispatch(savePassword({ currentPassword, newPassword }));
  };

  // eslint-disable-next-line @typescript-eslint/no-shadow
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
            <ValidatedField
              name="newPassword"
              label="New password"
              placeholder="New password"
              type={showPassword ? 'text' : 'password'} // Toggle type based on showPassword state
              value={password}
              onChange={updatePassword}
              validate={{
                required: { value: true, message: 'Your password is required.' },
                minLength: { value: 12, message: 'Your password is required to be at least 12 characters.' },
                maxLength: { value: 128, message: 'Your password cannot be longer than 128 characters.' },
              }}
              data-cy="newPassword"
            />
            <Button color="info" onClick={toggleShowPassword} className="mt-2">
              {showPassword ? 'Hide Password' : 'Show Password'}
            </Button>
            <PasswordStrengthBar password={password} />
            <ValidatedField
              name="confirmPassword"
              label="New password confirmation"
              placeholder="Confirm the new password"
              type={showConfirmPassword ? 'text' : 'password'} // Toggle type based on showConfirmPassword state
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
            <Button color="info" onClick={toggleShowConfirmPassword} className="mt-2 ml-2">
              {showConfirmPassword ? 'Hide Confirm Password' : 'Show Confirm Password'}
            </Button>
            <br />
            <br />
            <Button color="success" type="submit" data-cy="submit" className="mt-2 ml-2">
              Save
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordPage;
