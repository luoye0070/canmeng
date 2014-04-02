package lj.data



import org.junit.*
import grails.test.mixin.*

@TestFor(ProvinceInfoController)
@Mock(ProvinceInfo)
class ProvinceInfoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/provinceInfo/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.provinceInfoInstanceList.size() == 0
        assert model.provinceInfoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.provinceInfoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.provinceInfoInstance != null
        assert view == '/provinceInfo/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/provinceInfo/show/1'
        assert controller.flash.message != null
        assert ProvinceInfo.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/provinceInfo/list'

        populateValidParams(params)
        def provinceInfo = new ProvinceInfo(params)

        assert provinceInfo.save() != null

        params.id = provinceInfo.id

        def model = controller.show()

        assert model.provinceInfoInstance == provinceInfo
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/provinceInfo/list'

        populateValidParams(params)
        def provinceInfo = new ProvinceInfo(params)

        assert provinceInfo.save() != null

        params.id = provinceInfo.id

        def model = controller.edit()

        assert model.provinceInfoInstance == provinceInfo
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/provinceInfo/list'

        response.reset()

        populateValidParams(params)
        def provinceInfo = new ProvinceInfo(params)

        assert provinceInfo.save() != null

        // test invalid parameters in update
        params.id = provinceInfo.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/provinceInfo/edit"
        assert model.provinceInfoInstance != null

        provinceInfo.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/provinceInfo/show/$provinceInfo.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        provinceInfo.clearErrors()

        populateValidParams(params)
        params.id = provinceInfo.id
        params.version = -1
        controller.update()

        assert view == "/provinceInfo/edit"
        assert model.provinceInfoInstance != null
        assert model.provinceInfoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/provinceInfo/list'

        response.reset()

        populateValidParams(params)
        def provinceInfo = new ProvinceInfo(params)

        assert provinceInfo.save() != null
        assert ProvinceInfo.count() == 1

        params.id = provinceInfo.id

        controller.delete()

        assert ProvinceInfo.count() == 0
        assert ProvinceInfo.get(provinceInfo.id) == null
        assert response.redirectedUrl == '/provinceInfo/list'
    }
}
