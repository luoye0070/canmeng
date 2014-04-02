package lj.data



import org.junit.*
import grails.test.mixin.*

@TestFor(CityInfoController)
@Mock(CityInfo)
class CityInfoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/cityInfo/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.cityInfoInstanceList.size() == 0
        assert model.cityInfoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.cityInfoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.cityInfoInstance != null
        assert view == '/cityInfo/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/cityInfo/show/1'
        assert controller.flash.message != null
        assert CityInfo.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/cityInfo/list'

        populateValidParams(params)
        def cityInfo = new CityInfo(params)

        assert cityInfo.save() != null

        params.id = cityInfo.id

        def model = controller.show()

        assert model.cityInfoInstance == cityInfo
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/cityInfo/list'

        populateValidParams(params)
        def cityInfo = new CityInfo(params)

        assert cityInfo.save() != null

        params.id = cityInfo.id

        def model = controller.edit()

        assert model.cityInfoInstance == cityInfo
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/cityInfo/list'

        response.reset()

        populateValidParams(params)
        def cityInfo = new CityInfo(params)

        assert cityInfo.save() != null

        // test invalid parameters in update
        params.id = cityInfo.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/cityInfo/edit"
        assert model.cityInfoInstance != null

        cityInfo.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/cityInfo/show/$cityInfo.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        cityInfo.clearErrors()

        populateValidParams(params)
        params.id = cityInfo.id
        params.version = -1
        controller.update()

        assert view == "/cityInfo/edit"
        assert model.cityInfoInstance != null
        assert model.cityInfoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/cityInfo/list'

        response.reset()

        populateValidParams(params)
        def cityInfo = new CityInfo(params)

        assert cityInfo.save() != null
        assert CityInfo.count() == 1

        params.id = cityInfo.id

        controller.delete()

        assert CityInfo.count() == 0
        assert CityInfo.get(cityInfo.id) == null
        assert response.redirectedUrl == '/cityInfo/list'
    }
}
