import React, { useState, useEffect } from 'react';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';

import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { handleRegister, reset } from './register.reducer';

export const RegisterPage = () => {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const dispatch = useAppDispatch();

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [dispatch],
  );

  // eslint-disable-next-line @typescript-eslint/no-shadow
  const transformPassword = (password) => {
    return password.replace(/\s{2,}/g, ' ');
  };

  const handleValidSubmit = ({ username, email, firstPassword, secondPassword }) => {
    const transformedFirstPassword = transformPassword(firstPassword);
    const transformedSecondPassword = transformPassword(secondPassword);
    if (transformedFirstPassword !== transformedSecondPassword) {
      toast.error("The password and its confirmation do not match!");
      return;
    }
    dispatch(handleRegister({ login: username, email, password: transformedFirstPassword, langKey: 'en' }));
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

  const successMessage = useAppSelector(state => state.register.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    }
  }, [successMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1 id="register-title" data-cy="registerTitle">
            Registration
          </h1>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <ValidatedForm id="register-form" onSubmit={handleValidSubmit}>
            <ValidatedField
              name="username"
              label="Username"
              placeholder="Your username"
              validate={{
                required: { value: true, message: 'Your username is required.' },
                pattern: {
                  value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                  message: 'Your username is invalid.',
                },
                minLength: { value: 1, message: 'Your username is required to be at least 1 character.' },
                maxLength: { value: 50, message: 'Your username cannot be longer than 50 characters.' },
              }}
              data-cy="username"
            />
            <ValidatedField
              name="email"
              label="Email"
              placeholder="Your email"
              type="email"
              validate={{
                required: { value: true, message: 'Your email is required.' },
                minLength: { value: 5, message: 'Your email is required to be at least 5 characters.' },
                maxLength: { value: 254, message: 'Your email cannot be longer than 50 characters.' },
                validate: v => isEmail(v) || 'Your email is invalid.',
              }}
              data-cy="email"
            />
            <ValidatedField
              name="firstPassword"
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
              data-cy="firstPassword"
            />
            <Button color="info" onClick={toggleShowPassword}>
              {showPassword ? 'Hide Password' : 'Show Password'}
            </Button>
            <PasswordStrengthBar password={password} />
            <ValidatedField
              name="secondPassword"
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
              data-cy="secondPassword"
            />
            <Button color="info" onClick={toggleShowConfirmPassword}>
              {showConfirmPassword ? 'Hide Confirm Password' : 'Show Confirm Password'}
            </Button>
            <br />
            <br />
            <Button id="register-submit" color="primary" type="submit" data-cy="submit">
              Register
            </Button>
          </ValidatedForm>
          <p>&nbsp;</p>
          <Alert color="warning">
            <span>If you want to </span>
            <Link to="/login" className="alert-link">
              sign in
            </Link>
            <span>
              , you can try the default accounts:
              <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;) <br />- User (login=&quot;user&quot; and
              password=&quot;user&quot;).
            </span>
          </Alert>
        </Col>
      </Row>
    </div>
  );
};

export default RegisterPage;
