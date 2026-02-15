(function(global) {
    'use strict';

    var scenarios = [
        { id: 'scenario-001', name: '企业网络场景', type: 'enterprise', status: 'active', createdAt: '2026-02-01' },
        { id: 'scenario-002', name: '智能家居场景', type: 'personal', status: 'active', createdAt: '2026-02-05' },
        { id: 'scenario-003', name: '测试网络场景', type: 'test', status: 'inactive', createdAt: '2026-02-10' }
    ];

    var ScenarioManagement = {
        init: function() {
            window.onPageInit = function() {
                console.log('场景管理页面初始化完成');
                ScenarioManagement.renderScenarios();
                ScenarioManagement.updateStatusOverview();
            };
        },

        renderScenarios: function() {
            var tbody = document.getElementById('scenarioTableBody');
            tbody.innerHTML = '';
            scenarios.forEach(function(scenario) {
                var row = document.createElement('tr');
                row.innerHTML = '<td>' + scenario.name + '</td>' +
                    '<td>' + ScenarioManagement.getScenarioTypeName(scenario.type) + '</td>' +
                    '<td><span class="' + (scenario.status === 'active' ? 'nx-text-success' : 'nx-text-danger') + '">' + (scenario.status === 'active' ? '活跃' : '非活跃') + '</span></td>' +
                    '<td>' + scenario.createdAt + '</td>' +
                    '<td>' +
                    '<button class="nx-btn nx-btn--sm nx-btn--secondary" onclick="editScenario(\'' + scenario.id + '\')">编辑</button> ' +
                    '<button class="nx-btn nx-btn--sm nx-btn--danger" onclick="deleteScenario(\'' + scenario.id + '\')">删除</button>' +
                    '</td>';
                tbody.appendChild(row);
            });
        },

        updateStatusOverview: function() {
            document.getElementById('totalScenarios').textContent = scenarios.length;
            var activeCount = 0;
            var types = {};
            scenarios.forEach(function(s) {
                if (s.status === 'active') activeCount++;
                types[s.type] = true;
            });
            document.getElementById('activeScenarios').textContent = activeCount;
            document.getElementById('scenarioTypes').textContent = Object.keys(types).length;
        },

        getScenarioTypeName: function(type) {
            var typeMap = {
                'enterprise': '企业网络',
                'personal': '个人网络',
                'test': '测试网络',
                'development': '开发环境'
            };
            return typeMap[type] || type;
        },

        createScenario: function() {
            alert('创建场景功能开发中...');
        },

        editScenario: function(id) {
            alert('编辑场景: ' + id);
        },

        deleteScenario: function(id) {
            if (confirm('确定要删除该场景吗？')) {
                scenarios = scenarios.filter(function(s) { return s.id !== id; });
                ScenarioManagement.renderScenarios();
                ScenarioManagement.updateStatusOverview();
            }
        },

        refreshScenarios: function() {
            ScenarioManagement.renderScenarios();
        }
    };

    ScenarioManagement.init();

    global.createScenario = ScenarioManagement.createScenario;
    global.editScenario = ScenarioManagement.editScenario;
    global.deleteScenario = ScenarioManagement.deleteScenario;
    global.refreshScenarios = ScenarioManagement.refreshScenarios;

})(typeof window !== 'undefined' ? window : this);
