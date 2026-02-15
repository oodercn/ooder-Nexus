(function(global) {
    'use strict';

    var links = [
        { id: 'link-001', source: 'mcp-001', target: 'route-001', type: 'direct', status: 'active', createdAt: '2024-01-01', lastHeartbeat: '2024-01-10 10:00' },
        { id: 'link-002', source: 'mcp-001', target: 'route-002', type: 'direct', status: 'active', createdAt: '2024-01-01', lastHeartbeat: '2024-01-10 10:00' },
        { id: 'link-003', source: 'route-001', target: 'end-001', type: 'direct', status: 'active', createdAt: '2024-01-02', lastHeartbeat: '2024-01-10 09:55' },
        { id: 'link-004', source: 'route-002', target: 'end-002', type: 'relay', status: 'error', createdAt: '2024-01-02', lastHeartbeat: '2024-01-10 08:00' }
    ];

    var LinkManagement = {
        init: function() {
            window.onPageInit = function() {
                console.log('链路管理页面初始化完成');
                LinkManagement.renderLinks();
                LinkManagement.updateStats();
            };
        },

        renderLinks: function() {
            var tbody = document.getElementById('linksTableBody');
            tbody.innerHTML = '';
            links.forEach(function(link) {
                var row = document.createElement('tr');
                row.innerHTML = '\
                    <td>' + link.id + '</td>\
                    <td>' + link.source + '</td>\
                    <td>' + link.target + '</td>\
                    <td>' + (link.type === 'direct' ? '直连' : link.type === 'relay' ? '中继' : 'P2P') + '</td>\
                    <td><span class="' + (link.status === 'active' ? 'nx-text-success' : 'nx-text-danger') + '">' + (link.status === 'active' ? '活跃' : '异常') + '</span></td>\
                    <td>' + link.createdAt + '</td>\
                    <td>' + link.lastHeartbeat + '</td>\
                    <td>\
                        <button class="nx-btn nx-btn--sm nx-btn--secondary" onclick="editLink(\'' + link.id + '\')">编辑</button>\
                        <button class="nx-btn nx-btn--sm nx-btn--danger" onclick="removeLink(\'' + link.id + '\')">删除</button>\
                    </td>\
                ';
                tbody.appendChild(row);
            });
        },

        updateStats: function() {
            document.getElementById('linkCount').textContent = links.length;
            document.getElementById('activeLinkCount').textContent = links.filter(function(l) { return l.status === 'active'; }).length;
            document.getElementById('errorLinkCount').textContent = links.filter(function(l) { return l.status === 'error'; }).length;
            document.getElementById('avgResponseTime').textContent = '15ms';
            document.getElementById('packetSuccessRate').textContent = '98%';
            document.getElementById('bandwidthUsage').textContent = '45%';
        },

        addLink: function() {
            alert('添加链路功能开发中...');
        },

        editLink: function(id) {
            alert('编辑链路: ' + id);
        },

        removeLink: function(id) {
            if (confirm('确定要删除该链路吗？')) {
                links = links.filter(function(l) { return l.id !== id; });
                LinkManagement.renderLinks();
                LinkManagement.updateStats();
            }
        },

        refreshLinks: function() {
            LinkManagement.renderLinks();
            LinkManagement.updateStats();
        }
    };

    LinkManagement.init();

    global.addLink = LinkManagement.addLink;
    global.editLink = LinkManagement.editLink;
    global.removeLink = LinkManagement.removeLink;
    global.refreshLinks = LinkManagement.refreshLinks;
    global.LinkManagement = LinkManagement;

})(typeof window !== 'undefined' ? window : this);
