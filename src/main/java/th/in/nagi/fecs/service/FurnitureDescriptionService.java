package th.in.nagi.fecs.service;

import java.util.List;

import th.in.nagi.fecs.model.FurnitureDescription;

public interface FurnitureDescriptionService {

	FurnitureDescription findByKey(Integer id);

	void store(FurnitureDescription furnitureDescription);

	void update(FurnitureDescription furnitureDescription);

	List<FurnitureDescription> findAll();

	FurnitureDescription findBySerialNumber(String serialNumber);

	void removeBySerialNumber(String serialNumber);

	List<FurnitureDescription> findAndAscByName(int start, int size);

	List<FurnitureDescription> findAndDescByName(int start, int size);

	List<FurnitureDescription> findAndAscByPrice(int start, int size);

	List<FurnitureDescription> findAndDescByPrice(int start, int size);

}
