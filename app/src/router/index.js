import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Landing',
    component: () => import('../views/Landing.vue')
  },
  {
    path: '/test',
    name: 'TestPage',
    component: () => import('../views/TestPage.vue')
  },
  // 老人端路由
  {
    path: '/elderly',
    name: 'ElderlyHome',
    component: () => import('../views/elderly/Home.vue')
  },
  {
    path: '/elderly/ai-chat',
    name: 'ElderlyAIChat',
    component: () => import('../views/elderly/AIChat.vue')
  },
  {
    path: '/elderly/health-dashboard',
    name: 'ElderlyHealthDashboard',
    component: () => import('../views/elderly/HealthDashboard.vue')
  },
  {
    path: '/elderly/medicine-reminder',
    name: 'ElderlyMedicineReminder',
    component: () => import('../views/elderly/MedicineReminder.vue')
  },
  {
    path: '/elderly/emergency-contacts',
    name: 'ElderlyEmergencyContacts',
    component: () => import('../views/elderly/EmergencyContacts.vue')
  },
  {
    path: '/elderly/digital-album',
    name: 'ElderlyDigitalAlbum',
    component: () => import('../views/elderly/DigitalAlbum.vue')
  },
  {
    path: '/elderly/community',
    name: 'ElderlyCommunity',
    component: () => import('../views/elderly/Community.vue')
  },
  {
    path: '/elderly/account-security',
    name: 'ElderlyAccountSecurity',
    component: () => import('../views/elderly/AccountSecurity.vue')
  },
  {
    path: '/elderly/contacts',
    name: 'ElderlyContacts',
    component: () => import('../views/elderly/Contacts.vue')
  },
  {
    path: '/elderly/font-settings',
    name: 'ElderlyFontSettings',
    component: () => import('../views/elderly/FontSettings.vue')
  },
  {
    path: '/elderly/health-input',
    name: 'ElderlyHealthInput',
    component: () => import('../views/elderly/HealthInput.vue')
  },
  {
    path: '/elderly/health-records',
    name: 'ElderlyHealthRecords',
    component: () => import('../views/elderly/HealthRecords.vue')
  },
  {
    path: '/elderly/help',
    name: 'ElderlyHelp',
    component: () => import('../views/elderly/Help.vue')
  },
  {
    path: '/elderly/notifications',
    name: 'ElderlyNotifications',
    component: () => import('../views/elderly/Notifications.vue')
  },
  {
    path: '/elderly/services',
    name: 'ElderlyServices',
    component: () => import('../views/elderly/Services.vue')
  },
  {
    path: '/elderly/service-detail',
    name: 'ElderlyServiceDetail',
    component: () => import('../views/elderly/ServiceDetail.vue')
  },
  {
    path: '/elderly/settings',
    name: 'ElderlySettings',
    component: () => import('../views/elderly/Settings.vue')
  },
  { path: '/elderly/voice-settings', name: 'ElderlyVoiceSettings', component: () => import('../views/elderly/VoiceSettings.vue') },
  { path: '/elderly/emergency-response', name: 'ElderlyEmergencyResponse', component: () => import('../views/elderly/EmergencyResponse.vue') },
  { path: '/elderly/emergency-call', name: 'ElderlyEmergencyCall', component: () => import('../views/elderly/EmergencyCall.vue') },
  // 子女端路由
  {
    path: '/family',
    name: 'FamilyOverview',
    component: () => import('../views/family/Overview.vue')
  },
  {
    path: '/family/health-details',
    name: 'FamilyHealthDetails',
    component: () => import('../views/family/HealthDetails.vue')
  },
  {
    path: '/family/safety-guard',
    name: 'FamilySafetyGuard',
    component: () => import('../views/family/SafetyGuard.vue')
  },
  {
    path: '/family/message-center',
    name: 'FamilyMessageCenter',
    component: () => import('../views/family/MessageCenter.vue')
  },
  {
    path: '/family/health-report',
    name: 'FamilyHealthReport',
    component: () => import('../views/family/HealthReport.vue')
  },
  {
    path: '/family/care-interaction',
    name: 'FamilyCareInteraction',
    component: () => import('../views/family/CareInteraction.vue')
  },
  {
    path: '/family/relative-management',
    name: 'FamilyRelativeManagement',
    component: () => import('../views/family/RelativeManagement.vue')
  },
  {
    path: '/family/remote-assist',
    name: 'FamilyRemoteAssist',
    component: () => import('../views/family/RemoteAssist.vue')
  },
  {
    path: '/family/help',
    name: 'FamilyHelp',
    component: () => import('../views/family/Help.vue')
  },
  {
    path: '/family/settings',
    name: 'FamilySettings',
    component: () => import('../views/family/Settings.vue')
  },
  {
    path: '/family/emergency-assist',
    name: 'FamilyEmergencyAssist',
    component: () => import('../views/family/EmergencyAssist.vue')
  },
  // 管理员端路由
  {
    path: '/admin',
    name: 'AdminLogin',
    component: () => import('../views/admin/Login.vue')
  },
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    component: () => import('../views/admin/Dashboard.vue')
  },
  {
    path: '/admin/elderly-management',
    name: 'AdminElderlyManagement',
    component: () => import('../views/admin/ElderlyManagement.vue')
  },
  {
    path: '/admin/family-management',
    name: 'AdminFamilyManagement',
    component: () => import('../views/admin/FamilyManagement.vue')
  },
  {
    path: '/admin/logs',
    name: 'AdminLogs',
    component: () => import('../views/admin/Logs.vue')
  },
  {
    path: '/admin/policy-library',
    name: 'AdminPolicyLibrary',
    component: () => import('../views/admin/PolicyLibrary.vue')
  },
  {
    path: '/admin/system-settings',
    name: 'AdminSystemSettings',
    component: () => import('../views/admin/SystemSettings.vue')
  },
  {
    path: '/admin/work-orders',
    name: 'AdminWorkOrders',
    component: () => import('../views/admin/WorkOrders.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
