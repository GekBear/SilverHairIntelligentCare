import axios from 'axios';

// 创建axios实例
const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 从localStorage获取token
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器
api.interceptors.response.use(
  response => {
    return response.data;
  },
  error => {
    if (error.response) {
      // 处理错误响应
      switch (error.response.status) {
        case 401:
          // 未授权，清除token并跳转到登录页
          localStorage.removeItem('token');
          window.location.href = '/';
          break;
        case 404:
          console.error('请求的资源不存在');
          break;
        case 500:
          console.error('服务器内部错误');
          break;
        default:
          console.error('请求失败:', error.response.data);
      }
    } else if (error.request) {
      console.error('网络错误，无法连接到服务器');
    } else {
      console.error('请求配置错误:', error.message);
    }
    return Promise.reject(error);
  }
);

// 认证相关API
export const authAPI = {
  login: (phone, password) => {
    return api.post('/auth/login', null, {
      params: { phone, password }
    });
  },
  register: (user) => {
    return api.post('/auth/register', user);
  },
  getUserById: (id) => {
    return api.get(`/auth/user/${id}`);
  }
};

// 健康数据相关API
export const healthAPI = {
  createHealthRecord: (record) => {
    return api.post('/health/record', record);
  },
  getHealthRecords: (elderId) => {
    return api.get(`/health/records/${elderId}`);
  },
  getHealthRecord: (id) => {
    return api.get(`/health/record/${id}`);
  },
  getHealthReport: (elderId, type) => {
    return api.get(`/health/report/${elderId}?type=${type}`);
  }
};

// 安全相关API
export const safetyAPI = {
  createSos: (sos) => {
    return api.post('/safety/sos', sos);
  },
  getSosRecords: (elderId) => {
    return api.get(`/safety/sos/${elderId}`);
  },
  updateSosStatus: (id, status) => {
    return api.put(`/safety/sos/${id}/status`, { status });
  },
  createLocation: (location) => {
    return api.post('/safety/location', location);
  },
  getLocations: (elderId) => {
    return api.get(`/safety/locations/${elderId}`);
  },
  createFence: (fence) => {
    return api.post('/safety/fence', fence);
  },
  getFences: (elderId) => {
    return api.get(`/safety/fences/${elderId}`);
  },
  updateFence: (id, fence) => {
    return api.put(`/safety/fence/${id}`, fence);
  },
  deleteFence: (id) => {
    return api.delete(`/safety/fence/${id}`);
  }
};

// 老人相关API
export const elderlyAPI = {
  getElderlyList: (params) => {
    return api.get('/elderly/list', { params });
  },
  getElderlyById: (id) => {
    return api.get(`/elderly/${id}`);
  },
  createElderly: (elderly) => {
    return api.post('/elderly', elderly);
  },
  updateElderly: (id, elderly) => {
    return api.put(`/elderly/${id}`, elderly);
  },
  deleteElderly: (id) => {
    return api.delete(`/elderly/${id}`);
  }
};

// 子女相关API
export const familyAPI = {
  getFamilyList: (params) => {
    return api.get('/family/list', { params });
  },
  getFamilyById: (id) => {
    return api.get(`/family/${id}`);
  },
  createFamily: (family) => {
    return api.post('/family', family);
  },
  updateFamily: (id, family) => {
    return api.put(`/family/${id}`, family);
  },
  deleteFamily: (id) => {
    return api.delete(`/family/${id}`);
  }
};

// 工单相关API
export const workOrderAPI = {
  getWorkOrderList: (params) => {
    return api.get('/workorder/list', { params });
  },
  getWorkOrderById: (id) => {
    return api.get(`/workorder/${id}`);
  },
  createWorkOrder: (workOrder) => {
    return api.post('/workorder', workOrder);
  },
  updateWorkOrder: (id, workOrder) => {
    return api.put(`/workorder/${id}`, workOrder);
  },
  deleteWorkOrder: (id) => {
    return api.delete(`/workorder/${id}`);
  }
};

// 政策相关API
export const policyAPI = {
  getPolicyList: (params) => {
    return api.get('/policy/list', { params });
  },
  getPolicyById: (id) => {
    return api.get(`/policy/${id}`);
  },
  createPolicy: (policy) => {
    return api.post('/policy', policy);
  },
  updatePolicy: (id, policy) => {
    return api.put(`/policy/${id}`, policy);
  },
  deletePolicy: (id) => {
    return api.delete(`/policy/${id}`);
  }
};

// 系统相关API
export const systemAPI = {
  getSystemSettings: () => {
    return api.get('/system/settings');
  },
  updateSystemSettings: (settings) => {
    return api.put('/system/settings', settings);
  },
  getSystemLogs: (params) => {
    return api.get('/system/logs', { params });
  }
};

export default api;