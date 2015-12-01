package th.in.nagi.fecs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.in.nagi.fecs.model.Catalog;
import th.in.nagi.fecs.model.Category;
import th.in.nagi.fecs.model.ProductDescription;
import th.in.nagi.fecs.model.SubCategory;
import th.in.nagi.fecs.model.Type;
import th.in.nagi.fecs.repository.CatalogRepository;
import th.in.nagi.fecs.repository.TypeRepository;

/**
 * Provide Category service for managing category easier. Ex. add, edit, delete,
 * find.
 * 
 * @author Thanachote Visetsuthimont
 *
 */
@Service("catalogService")
@Transactional
public class CatalogService {

	@Autowired
	private CatalogRepository catalogRepository;
	
	@Autowired
	private TypeRepository typeRepository;

	public Catalog findByKey(Integer id) {
		return catalogRepository.findByKey(id);
	}

	public void store(Catalog catalog) {
		catalogRepository.store(catalog);

	}

	public void update(Catalog catalog) {
		Catalog entity = catalogRepository.findByKey(catalog.getId());
		if (entity != null) {
			if (catalog.getType() != null){
				entity.setType(catalog.getType());
			}
			if (catalog.getProductDescription() != null){
				entity.setProductDescription(catalog.getProductDescription());
			}
		}

	}

	public void removeById(Integer id) {
		catalogRepository.remove(id);
	}

	public List<Catalog> findAll() {
		return catalogRepository.findAll();
	}

	public List<Catalog> findAndAscByName(int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Catalog> findAndDescByName(int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean createCatalog(Category category, SubCategory subCategory, ProductDescription productDescription) {
		Type type = typeRepository.findByCategoryAndSubCategory(category, subCategory);
		if (type == null){
			return false;
		}
		Catalog catalog = new Catalog();
		catalog.setType(type);
		catalog.setProductDescription(productDescription);
		this.store(catalog);
		return true;
	}
	
	public boolean createCatalog(Type type, ProductDescription productDescription) {
		if (type == null){
			return false;
		}
		Catalog catalog = new Catalog();
		catalog.setType(type);
		catalog.setProductDescription(productDescription);
		this.store(catalog);
		return true;
	}
	
	public List<ProductDescription> findProductByType(Type type) {
		List<ProductDescription> productDescriptions = new ArrayList<>();
		for (Catalog catalog:catalogRepository.findByType(type)){
			productDescriptions.add(catalog.getProductDescription());
		}
		return productDescriptions;
			
	}
	
	public List<Type> findTypeByProduct(ProductDescription product){
		List<Type> type = new ArrayList<>();
		for (Catalog catalog:catalogRepository.findByProduct(product)){
			type.add(catalog.getType());
		}
		return type;
	}
	
	public List<Catalog> findCatalogByProduct(ProductDescription product){
		List<Catalog> catalog = catalogRepository.findByProduct(product);
		return catalog;
	}

}
