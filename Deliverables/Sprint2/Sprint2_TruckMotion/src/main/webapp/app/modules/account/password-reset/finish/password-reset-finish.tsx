import React, { useState, useEffect } from 'react';
import { Col, Row, Button } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';

import { handlePasswordResetFinish, reset } from '../password-reset.reducer';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PasswordResetFinishPage = () => {
  const dispatch = useAppDispatch();

  const [searchParams] = useSearchParams();
  const key = searchParams.get('key');

  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [],
  );

  // eslint-disable-next-line @typescript-eslint/no-shadow
  const handleValidSubmit = ({ newPassword, confirmPassword }) => {
    const transformedFirstPassword = transformPassword(newPassword);
    const transformedSecondPassword = transformPassword(confirmPassword);
    if (transformedFirstPassword !== transformedSecondPassword) {
      toast.error("The password and its confirmation do not match!");
      return;
    }
    dispatch(handlePasswordResetFinish({ key, newPassword }));
  }

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

  const getResetForm = () => {
    return (
      <ValidatedForm onSubmit={handleValidSubmit}>
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
          data-cy="resetPassword"
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
          data-cy="confirmResetPassword"
        />
        <Button color="info" onClick={toggleShowConfirmPassword} className="mt-2 ml-2">
          {showConfirmPassword ? 'Hide Confirm Password' : 'Show Confirm Password'}
        </Button>
        <br />
        <br />
        <Button color="success" type="submit" data-cy="submit">
          Validate new password
        </Button>
      </ValidatedForm>
    );
  };

  const successMessage = useAppSelector(state => state.passwordReset.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    }
  }, [successMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="4">
          <h1>Reset password</h1>
          <div>{key ? getResetForm() : null}</div>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordResetFinishPage;
