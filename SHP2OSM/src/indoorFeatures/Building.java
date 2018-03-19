package indoorFeatures;

public class Building implements IIndoorFeature {
    
    String addrCity;
    String addrHouseName;
    String addrHouseNumber;
    String addrPostcode;
    String addrHouseStreet;
    boolean isBuilding;
    int levels;
    String type;
    int buildingId;
    String buildingName;
    
    public Building(String addrCity, String addrHouseName, String addrHouseNumber,
		    String addrPostcode, String addrHouseStreet, boolean isBuilding, int levels, String type,
		    int buildingId, String buildingName) {
	this.addrCity = addrCity;
	this.addrHouseName = addrHouseName;
	this.addrHouseNumber = addrHouseNumber;
	this.addrPostcode = addrPostcode;
	this.addrHouseStreet = addrHouseStreet;
	this.isBuilding = isBuilding;
	this.levels = levels;
	this.type = type;
	this.buildingId = buildingId;
	this.buildingName = buildingName;
    }
    
    public String getAddrCity() {
	return addrCity;
    }
    
    public void setAddrCity(String addrCity) {
	this.addrCity = addrCity;
    }
    
    public String getAddrHouseName() {
	return addrHouseName;
    }
    
    public void setAddrHouseName(String addrHouseName) {
	this.addrHouseName = addrHouseName;
    }
    
    public String getAddrHouseNumber() {
	return addrHouseNumber;
    }
    
    public void setAddrHouseNumber(String addrHouseNumber) {
	this.addrHouseNumber = addrHouseNumber;
    }
    
    public String getAddrPostcode() {
	return addrPostcode;
    }
    
    public void setAddrPostcode(String addrPostcode) {
	this.addrPostcode = addrPostcode;
    }
    
    public String getAddrHouseStreet() {
	return addrHouseStreet;
    }
    
    public void setAddrHouseStreet(String addrHouseStreet) {
	this.addrHouseStreet = addrHouseStreet;
    }
    
    public boolean isBuilding() {
	return isBuilding;
    }
    
    public void setBuilding(boolean building) {
	isBuilding = building;
    }
    
    public int getLevels() {
	return levels;
    }
    
    public void setLevels(int levels) {
	this.levels = levels;
    }
    
    public String getType() {
	return type;
    }
    
    public void setType(String type) {
	this.type = type;
    }
    
    public int getBuildingId() {
	return buildingId;
    }
    
    public void setBuildingId(int buildingId) {
	this.buildingId = buildingId;
    }
    
    public String getBuildingName() {
	return buildingName;
    }
    
    public void setBuildingName(String buildingName) {
	this.buildingName = buildingName;
    }
}
