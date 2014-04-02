package lj.data



import org.junit.*
import grails.test.mixin.*

@TestFor(AreaInfoController)
@Mock(AreaInfo)
class AreaInfoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/areaInfo/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.areaInfoInstanceList.size() == 0
        assert model.areaInfoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.areaInfoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.areaInfoInstance != null
        assert view == '/areaInfo/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/areaInfo/show/1'
        assert controller.flash.message != null
        assert AreaInfo.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/areaInfo/list'

        populateValidParams(params)
        def areaInfo = new AreaInfo(params)

        assert areaInfo.save() != null

        params.id = areaInfo.id

        def model = controller.show()

        assert model.areaInfoInstance == areaInfo
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/areaInfo/list'

        populateValidParams(params)
        def areaInfo = new AreaInfo(params)

        assert areaInfo.save() != null

        params.id = areaInfo.id

        def model = controller.edit()

        assert model.areaInfoInstance == areaInfo
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/areaInfo/list'

        response.reset()

        populateValidParams(params)
        def areaInfo = new AreaInfo(params)

        assert areaInfo.save() != null

        // test invalid parameters in update
        params.id = areaInfo.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/areaInfo/edit"
        assert model.areaInfoInstance != null

        areaInfo.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/areaInfo/show/$areaInfo.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        areaInfo.clearErrors()

        populateValidParams(params)
        params.id = areaInfo.id
        params.version = -1
        controller.update()

        assert view == "/areaInfo/edit"
        assert model.areaInfoInstance != null
        assert model.areaInfoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/areaInfo/list'

        response.reset()

        populateValidParams(params)
        def areaInfo = new AreaInfo(params)

        assert areaInfo.save() != null
        assert AreaInfo.count() == 1

        params.id = areaInfo.id

        controller.delete()

        assert AreaInfo.count() == 0
        assert AreaInfo.get(areaInfo.id) == null
        assert response.redirectedUrl == '/areaInfo/list'
    }
}
