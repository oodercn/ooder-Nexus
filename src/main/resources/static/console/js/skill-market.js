class SkillMarket {
    constructor() {
        this.skillList = [];
        this.categories = [];
        this.filter = {
            category: '',
            keyword: ''
        };
        this.currentPreviewSkill = null;
    }

    async init() {
        try {
            await this.loadSkillMarket();
        } catch (error) {
            console.error('初始化技能市场失败:', error);
            this.skillList = [];
            this.renderSkillMarket();
        }
        this.bindEvents();
    }

    async loadSkillMarket() {
        try {
            const skillMarketGrid = document.getElementById('skill-market-grid');
            skillMarketGrid.innerHTML = `
                <div class="loading">
                    <i class="ri-loader-4-line ri-spin" style="font-size: 32px;"></i>
                    <p>加载中...</p>
                </div>
            `;
            
            // 从本地JSON文件加载技能数据
            const response = await fetch('/console/data/skill-market-data.json');
            const data = await response.json();
            
            if (data && data.code === 200 && data.data) {
                this.skillList = data.data;
                // 应用筛选
                this.applyFilter();
            } else {
                this.skillList = [];
                this.renderSkillMarket();
            }
        } catch (error) {
            console.error('加载技能市场失败:', error);
            this.skillList = [];
            this.renderSkillMarket();
            this.showError('加载技能市场失败，请检查网络连接');
        }
    }

    applyFilter() {
        let filteredSkills = this.skillList;
        
        // 分类筛选
        if (this.filter.category) {
            filteredSkills = filteredSkills.filter(skill => skill.category === this.filter.category);
        }
        
        // 关键词搜索
        if (this.filter.keyword) {
            const keyword = this.filter.keyword.toLowerCase();
            filteredSkills = filteredSkills.filter(skill => 
                skill.skillName.toLowerCase().includes(keyword) ||
                skill.description.toLowerCase().includes(keyword) ||
                skill.tags.some(tag => tag.toLowerCase().includes(keyword))
            );
        }
        
        this.renderSkillMarket(filteredSkills);
    }

    renderSkillMarket(skills = this.skillList) {
        const skillMarketGrid = document.getElementById('skill-market-grid');
        
        if (skills.length === 0) {
            skillMarketGrid.innerHTML = `
                <div class="empty-state" style="grid-column: 1 / -1;">
                    <i class="ri-store-3-line"></i>
                    <h3>暂无技能</h3>
                    <p>没有找到匹配的技能，请尝试其他搜索条件</p>
                </div>
            `;
            return;
        }

        let html = '';
        skills.forEach(skill => {
            const meta = skill.metadata || {};
            const tags = meta.tags || [];
            const capabilities = meta.capabilities || [];
            
            html += `
                <div class="skill-card" data-skill-id="${skill.skillId}">
                    <div class="skill-card-header">
                        <div class="skill-icon">
                            <i class="${meta.icon || 'ri-code-line'}"></i>
                        </div>
                        <div class="skill-info">
                            <div class="skill-name">${skill.skillName}</div>
                            <span class="skill-category">${skill.category}</span>
                        </div>
                    </div>
                    <div class="skill-description">${skill.description}</div>
                    <div class="skill-meta">
                        <span><i class="ri-user-line"></i> ${meta.author || '未知'}</span>
                        <span><i class="ri-star-fill"></i> ${meta.rating || 0}</span>
                        <span><i class="ri-download-line"></i> ${this.formatNumber(meta.downloads || 0)}</span>
                        <span><i class="ri-code-box-line"></i> ${meta.version || '1.0.0'}</span>
                    </div>
                    <div class="skill-tags">
                        ${tags.slice(0, 3).map(tag => `<span class="skill-tag">${tag}</span>`).join('')}
                    </div>
                    <div class="skill-actions">
                        <button class="btn btn-secondary" onclick="skillMarket.previewSkill('${skill.skillId}')">
                            <i class="ri-eye-line"></i> 预览
                        </button>
                        <button class="btn btn-primary" onclick="skillMarket.installSkill('${skill.skillId}')">
                            <i class="ri-download-line"></i> 安装
                        </button>
                    </div>
                </div>
            `;
        });

        skillMarketGrid.innerHTML = html;
    }

    formatNumber(num) {
        if (num >= 10000) {
            return (num / 10000).toFixed(1) + 'w';
        } else if (num >= 1000) {
            return (num / 1000).toFixed(1) + 'k';
        }
        return num.toString();
    }

    bindEvents() {
        // 绑定搜索事件
        const searchInput = document.getElementById('search-input');
        if (searchInput) {
            searchInput.addEventListener('input', (e) => {
                this.filter.keyword = e.target.value;
                this.applyFilter();
            });
        }

        // 绑定分类筛选事件
        const categoryFilter = document.getElementById('category-filter');
        if (categoryFilter) {
            categoryFilter.addEventListener('change', (e) => {
                this.filter.category = e.target.value;
                this.applyFilter();
            });
        }
    }

    async installSkill(skillId) {
        try {
            const skill = this.skillList.find(s => s.skillId === skillId);
            if (!skill) {
                this.showError('技能不存在');
                return;
            }

            // 模拟安装过程
            this.showSuccess(`正在安装 ${skill.skillName}...`);
            
            setTimeout(() => {
                this.showSuccess(`${skill.skillName} 安装成功！`);
            }, 1500);
            
        } catch (error) {
            console.error('安装技能失败:', error);
            this.showError('安装技能失败，请检查网络连接');
        }
    }

    async previewSkill(skillId) {
        try {
            const skill = this.skillList.find(s => s.skillId === skillId);
            if (!skill) {
                this.showError('技能不存在');
                return;
            }

            this.currentPreviewSkill = skill;
            const meta = skill.metadata || {};
            
            // 填充预览信息
            document.getElementById('preview-skill-name').textContent = skill.skillName;
            document.getElementById('preview-skill-description').textContent = skill.description;
            document.getElementById('preview-skill-version').textContent = meta.version || '1.0.0';
            document.getElementById('preview-skill-author').textContent = meta.author || '未知';
            document.getElementById('preview-skill-category').textContent = skill.category;
            document.getElementById('preview-skill-rating').textContent = (meta.rating || 0) + ' 分';
            document.getElementById('preview-skill-downloads').textContent = this.formatNumber(meta.downloads || 0);
            
            const iconEl = document.getElementById('preview-skill-icon');
            iconEl.className = meta.icon || 'ri-code-line';
            
            // 填充功能特性
            const capabilitiesList = document.getElementById('preview-skill-capabilities');
            const capabilities = meta.capabilities || [];
            capabilitiesList.innerHTML = capabilities.map(cap => `<li>${cap}</li>`).join('');
            
            // 显示模态框
            document.getElementById('skill-preview-modal').classList.add('active');
            
        } catch (error) {
            console.error('预览技能失败:', error);
            this.showError('预览技能失败');
        }
    }

    showSuccess(message) {
        this.showNotification(message, 'success');
    }

    showError(message) {
        this.showNotification(message, 'error');
    }

    showNotification(message, type) {
        // 移除现有通知
        const existingNotification = document.querySelector('.notification');
        if (existingNotification) {
            existingNotification.remove();
        }
        
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.innerHTML = `
            <i class="ri-${type === 'success' ? 'check-line' : 'error-warning-line'}"></i>
            ${message}
        `;
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.style.animation = 'slideOut 0.3s ease forwards';
            setTimeout(() => notification.remove(), 300);
        }, 3000);
    }
}

let skillMarket;
document.addEventListener('DOMContentLoaded', () => {
    skillMarket = new SkillMarket();
    skillMarket.init();
});

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('active');
    }
}

async function refreshSkillMarket() {
    if (skillMarket) {
        await skillMarket.loadSkillMarket();
    } else {
        alert('页面未完全加载，请刷新后重试');
    }
}

async function installSkillFromPreview() {
    if (skillMarket && skillMarket.currentPreviewSkill) {
        await skillMarket.installSkill(skillMarket.currentPreviewSkill.skillId);
        closeModal('skill-preview-modal');
    }
}

// 点击模态框外部关闭
window.onclick = function(event) {
    const modal = document.getElementById('skill-preview-modal');
    if (event.target === modal) {
        closeModal('skill-preview-modal');
    }
}
