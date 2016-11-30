

package com.digitechlabs.neat.entities;

import android.graphics.Bitmap;
import android.widget.TextView;

/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class 
ProductSubCatModel {
	public int TotalRowCount;
	public String Description;
	public int MDProductCategoryID;
    private String Name;
    public String Path;
	public int ResturantID;
	public int getTotalRowCount() {
		return TotalRowCount;
	}
	public void setTotalRowCount(int totalRowCount) {
		TotalRowCount = totalRowCount;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public int getMDProductCategoryID() {
		return MDProductCategoryID;
	}
	public void setMDProductCategoryID(int mDProductCategoryID) {
		MDProductCategoryID = mDProductCategoryID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	public int getResturantID() {
		return ResturantID;
	}
	public void setResturantID(int resturantID) {
		ResturantID = resturantID;
	}
	@Override
	public String toString() {
		return "ProductSubCatModel [TotalRowCount=" + TotalRowCount + ", Description=" + Description
				+ ", MDProductCategoryID=" + MDProductCategoryID + ", Name=" + Name + ", Path=" + Path
				+ ", ResturantID=" + ResturantID + "]";
	}
   
    
    
    
}
