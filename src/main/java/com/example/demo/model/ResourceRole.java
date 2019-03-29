package com.example.demo.model;

import java.util.List;

public class ResourceRole {
	 		 private Integer id;

	    private String name;

	    private String resUrl;

	    private Integer type;

	    private Integer parentId;

	    private Integer sort;

	    private String resKey;
	    		
	    private List<TRole> trole;

		public ResourceRole() {
			
		}

		public ResourceRole(Integer id) {
			super();
			this.id = id;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getResUrl() {
			return resUrl;
		}

		public void setResUrl(String resUrl) {
			this.resUrl = resUrl;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Integer getParentId() {
			return parentId;
		}

		public void setParentId(Integer parentId) {
			this.parentId = parentId;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}

		public String getResKey() {
			return resKey;
		}

		public void setResKey(String resKey) {
			this.resKey = resKey;
		}

		public List<TRole> getTrole() {
			return trole;
		}

		public void setTrole(List<TRole> trole) {
			this.trole = trole;
		}

		@Override
		public String toString() {
			return "ResourceRole [id=" + id + ", name=" + name + ", resUrl=" + resUrl + ", type=" + type + ", parentId="
					+ parentId + ", sort=" + sort + ", resKey=" + resKey + ", trole=" + trole + "]";
		}

}
