import { Request as ExpressRequest, Response, NextFunction } from 'express';
import jwt from 'jsonwebtoken';
import UserModel from '../models/user';
import UserRoleModel from '../models/userRole';
import RoleModel from '../models/role';

interface DecodedToken {
  userId: string;
}

declare global {
  namespace Express {
    interface Request {
      userId?: string;
    }
  }
}

const authAdminMiddleware = async (req: ExpressRequest, res: Response, next: NextFunction) => {
  const authHeader = req.header('Authorization');

  if (!authHeader) {
    return res.status(401).json({ message: 'Authorization failed: No authorization header provided' });
  }

  const token = authHeader.replace('Bearer ', '');

  if (!token) {
    return res.status(401).json({ message: 'Authorization failed: No token provided' });
  }

  try {
    if (!process.env.JWT_SECRET) {
      throw new Error('JWT secret is not defined in environment variables');
    }

    const secret = process.env.JWT_SECRET as jwt.Secret;
    const decoded = jwt.verify(token, secret) as DecodedToken;

    const user = await UserModel.findById(decoded.userId);
    if (!user) {
      return res.status(401).json({ message: 'Unauthorized' });
    }

    const userRole = await UserRoleModel.findByUserId(user.UserId);
    if (!userRole) {
      return res.status(401).json({ message: 'Unauthorized' });
    }

    const role = await RoleModel.findById(userRole.RoleId);
    if (!role || role.Name !== 'Admin') {
      return res.status(403).json({ message: 'Forbidden' });
    }

    req.userId = user.UserId;
    next();
  } catch (error) {
    console.error('Auth Middleware Error:', error);
    return res.status(500).json({ message: 'Internal Server Error' });
  }
};

export default authAdminMiddleware;

// import { Request, Response, NextFunction } from 'express';
// import jwt from 'jsonwebtoken';
// import UserModel from '../models/user';
// import UserRoleModel from '../models/userRole';
// import RoleModel from '../models/role';

// interface AuthenticatedRequest extends Request {
//   user: { userId: string };
// }

// export const authAdminMiddleware = async (req: AuthenticatedRequest, res: Response, next: NextFunction) => {
//   const token = req.header('Authorization')?.replace('Bearer ', '');
//   if (!token) {
//     return res.status(401).json({ message: 'Unauthorized' });
//   }

//   try {
//     const decoded = jwt.verify(token, process.env.JWT_SECRET || '') as { userId: string };
//     const user = await UserModel.findById(decoded.userId);

//     if (!user) {
//       return res.status(401).json({ message: 'Unauthorized' });
//     }

//     const userRole = await UserRoleModel.findByUserId(user.UserId);
//     if (!userRole) {
//       return res.status(401).json({ message: 'Unauthorized' });
//     }

//     const role = await RoleModel.findById(userRole.RoleId);
//     if (!role || role.RoleName !== 'admin') {
//       return res.status(403).json({ message: 'Forbidden' });
//     }

//     req.user = { userId: user.UserId };
//     next();
//   } catch (err) {
//     console.error('Auth Middleware Error:', err);
//     return res.status(500).json({ message: 'Internal Server Error' });
//   }
// };

// export default authAdminMiddleware;
