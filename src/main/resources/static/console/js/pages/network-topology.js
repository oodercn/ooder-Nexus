(function(global) {
    'use strict';

    var topologyData = {
        nodes: [
            { id: 'mcp-001', name: '主Nexus', type: 'mcp', x: 400, y: 80, status: 'active' },
            { id: 'route-001', name: '路由代理1', type: 'route', x: 250, y: 200, status: 'active' },
            { id: 'route-002', name: '路由代理2', type: 'route', x: 550, y: 200, status: 'active' },
            { id: 'end-001', name: '智能灯泡', type: 'end', x: 150, y: 320, status: 'active' },
            { id: 'end-002', name: '智能插座', type: 'end', x: 350, y: 320, status: 'active' },
            { id: 'end-003', name: '摄像头', type: 'end', x: 450, y: 320, status: 'inactive' },
            { id: 'end-004', name: '智能音箱', type: 'end', x: 650, y: 320, status: 'active' }
        ],
        connections: [
            { source: 'mcp-001', target: 'route-001', status: 'active' },
            { source: 'mcp-001', target: 'route-002', status: 'active' },
            { source: 'route-001', target: 'end-001', status: 'active' },
            { source: 'route-001', target: 'end-002', status: 'active' },
            { source: 'route-002', target: 'end-003', status: 'inactive' },
            { source: 'route-002', target: 'end-004', status: 'active' }
        ]
    };

    var NetworkTopology = {
        init: function() {
            window.onPageInit = function() {
                console.log('网络拓扑页面初始化完成');
                NetworkTopology.renderTopology();
                NetworkTopology.updateStatusOverview();
            };
        },

        renderTopology: function() {
            var svg = document.getElementById('topologySvg');
            svg.innerHTML = '';

            topologyData.connections.forEach(function(conn) {
                var source = topologyData.nodes.find(function(n) { return n.id === conn.source; });
                var target = topologyData.nodes.find(function(n) { return n.id === conn.target; });
                if (source && target) {
                    var line = document.createElementNS('http://www.w3.org/2000/svg', 'line');
                    line.setAttribute('x1', source.x);
                    line.setAttribute('y1', source.y);
                    line.setAttribute('x2', target.x);
                    line.setAttribute('y2', target.y);
                    line.setAttribute('stroke', conn.status === 'active' ? '#22c55e' : '#ef4444');
                    line.setAttribute('stroke-width', '2');
                    svg.appendChild(line);
                }
            });

            topologyData.nodes.forEach(function(node) {
                var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');

                var circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
                circle.setAttribute('cx', node.x);
                circle.setAttribute('cy', node.y);
                circle.setAttribute('r', '25');
                circle.setAttribute('fill', NetworkTopology.getNodeColor(node.type));
                circle.setAttribute('stroke', node.status === 'active' ? '#22c55e' : '#ef4444');
                circle.setAttribute('stroke-width', '3');

                var text = document.createElementNS('http://www.w3.org/2000/svg', 'text');
                text.setAttribute('x', node.x);
                text.setAttribute('y', node.y + 5);
                text.setAttribute('text-anchor', 'middle');
                text.setAttribute('fill', 'white');
                text.setAttribute('font-size', '12');
                text.textContent = node.name.substring(0, 4);

                var label = document.createElementNS('http://www.w3.org/2000/svg', 'text');
                label.setAttribute('x', node.x);
                label.setAttribute('y', node.y + 45);
                label.setAttribute('text-anchor', 'middle');
                label.setAttribute('fill', 'var(--nx-text-primary)');
                label.setAttribute('font-size', '11');
                label.textContent = node.name;

                g.appendChild(circle);
                g.appendChild(text);
                g.appendChild(label);
                svg.appendChild(g);
            });
        },

        getNodeColor: function(type) {
            var colors = { mcp: '#3b82f6', route: '#22c55e', end: '#f59e0b', gateway: '#8b5cf6' };
            return colors[type] || '#64748b';
        },

        updateStatusOverview: function() {
            document.getElementById('totalNodes').textContent = topologyData.nodes.length;
            document.getElementById('totalConnections').textContent = topologyData.connections.length;
            var activeCount = 0;
            topologyData.nodes.forEach(function(n) {
                if (n.status === 'active') activeCount++;
            });
            document.getElementById('activeNodes').textContent = activeCount;
        },

        refreshTopology: function() {
            NetworkTopology.renderTopology();
        },

        exportTopology: function() {
            var data = JSON.stringify(topologyData, null, 2);
            var blob = new Blob([data], { type: 'application/json' });
            var url = URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            a.download = 'network-topology.json';
            a.click();
            URL.revokeObjectURL(url);
        }
    };

    NetworkTopology.init();

    global.refreshTopology = NetworkTopology.refreshTopology;
    global.exportTopology = NetworkTopology.exportTopology;

})(typeof window !== 'undefined' ? window : this);
