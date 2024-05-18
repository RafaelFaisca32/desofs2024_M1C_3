import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IApplicationUser {
  id?: number;
  name?: string | null;
  birthDate?: dayjs.Dayjs | null;
  email?: string | null;
  gender?: keyof typeof Gender | null;
  internalUser?: IUser | null;
}

export const defaultValue: Readonly<IApplicationUser> = {};
